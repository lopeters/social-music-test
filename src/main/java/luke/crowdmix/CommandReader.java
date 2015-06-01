package luke.crowdmix;

import luke.crowdmix.message.Message;
import luke.crowdmix.message.MessageRepository;
import luke.crowdmix.user.User;
import luke.crowdmix.user.UserFactory;
import luke.crowdmix.user.UserRepository;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

import static luke.crowdmix.user.User.Name.username;
import static luke.crowdmix.util.DateUtil.describeDurationBetween;

public class CommandReader {
    private final UserRepository userRepository;
    private final PrintStream output;
    private final Supplier<LocalDateTime> localDateTimeSupplier;

    public CommandReader(UserRepository userRepository, PrintStream output, Supplier<LocalDateTime> localDateTimeSupplier) {
        this.userRepository = userRepository;
        this.output = output;
        this.localDateTimeSupplier = localDateTimeSupplier;
    }

    public static void main(String[] args) {
        commandReaderWithProductionConfiguration().consume(new Scanner(System.in));
    }

    public static CommandReader commandReaderWithProductionConfiguration() {
        Supplier<LocalDateTime> dateTimeSupplier = LocalDateTime::now;
        MessageRepository messageRepository = new MessageRepository(dateTimeSupplier);
        return new CommandReader(new UserRepository(new UserFactory(messageRepository)), System.out, dateTimeSupplier);
    }

    public void consume(Scanner scanner) {
        while (scanner.hasNextLine()) {
            new Command(scanner.nextLine()).execute();
        }
    }

    public class Command {
        private static final String POST_COMMAND = "->";
        private static final String FOLLOW_COMMAND = "follows";
        private static final String WALL_COMMAND = "wall";

        private final User.Name username;
        private final Optional<String> restOfCommand;
        private final Optional<String> operation;
        private final String input;

        public Command(String input) {
            this.input = input;
            String[] split = input.split(" ", 3);
            this.username = username(split[0]);
            this.operation = split.length > 1 ? Optional.of(split[1]) : Optional.empty();
            this.restOfCommand = split.length > 2 ? Optional.of(split[2]) : Optional.empty();
        }

        public void execute() {
            User user = userRepository.get(username);
            if (!operation.isPresent()) {
                user.messages().forEach(this::printMessage);
            } else if (POST_COMMAND.equals(operation.get())) {
                user.publish(restOfCommand.get());
            } else if (WALL_COMMAND.equals(operation.get())) {
                user.wall().forEach(this::printMessageAndUserName);
            } else if (FOLLOW_COMMAND.equals(operation.get())) {
                user.follow(username(restOfCommand.get()));
            } else {
                System.out.println("unknown command: " + input);
            }
        }

        private void printMessage(Message msg) {
            output.println(formatMessage(msg));
        }

        private void printMessageAndUserName(Message msg) {
            output.println(msg.userName() + " - " + formatMessage(msg));
        }

        private String formatMessage(Message msg) {
            return msg.message() + " (" + describeDurationBetween(msg.dateCreated(), localDateTimeSupplier.get()) + " ago)";
        }
    }
}
