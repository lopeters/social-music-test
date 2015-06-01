package luke.crowdmix;

import luke.crowdmix.user.User;
import luke.crowdmix.user.UserRepository;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

import static luke.crowdmix.user.User.Name.username;

public class CommandReader {
    public static final String POST_COMMAND = "-> ";

    private final UserRepository userRepository;
    private final PrintStream output;

    public CommandReader(UserRepository userRepository, PrintStream output) {
        this.userRepository = userRepository;
        this.output = output;
    }

    public void consume(Scanner scanner) {
        while (scanner.hasNextLine()) {
            new Command(scanner.nextLine()).execute();
        }
    }

    public class Command {
        private final User.Name username;
        private final Optional<String> restOfCommand;
        private final String input;

        public Command(String input) {
            this.input = input;
            String[] split = input.split(" ", 2);
            this.username = username(split[0]);
            this.restOfCommand = split.length > 1 ? Optional.of(split[1]) : Optional.empty();
        }

        public void execute() {
            if (isAPost()) {
                userRepository.get(username).publish(parseMessageToPost());
            } else if (!restOfCommand.isPresent()) {
                userRepository.get(username).messages().forEach((msg) -> output.println(msg.toString()));
            } else {
                System.out.println("unknown command: " + input);
            }
        }

        private boolean isAPost() {
            return restOfCommand.isPresent() && restOfCommand.get().startsWith(POST_COMMAND);
        }

        private String parseMessageToPost() {
            return restOfCommand.get().substring(POST_COMMAND.length());
        }
    }
}
