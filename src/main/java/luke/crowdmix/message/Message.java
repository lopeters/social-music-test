package luke.crowdmix.message;

import luke.crowdmix.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public class Message {
    private final String message;
    private final LocalDateTime dateCreated;
    private final User.Name userName;

    public Message(String message, LocalDateTime dateCreated, User.Name userName) {
        this.message = message;
        this.dateCreated = dateCreated;
        this.userName = userName;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    public String message() {
        return message;
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object tother) {
        return EqualsBuilder.reflectionEquals(this, tother);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public User.Name userName() {
        return userName;
    }
}
