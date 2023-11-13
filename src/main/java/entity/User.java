package entity;

import java.time.LocalDateTime;

public abstract class User {

    abstract public String getName();

    abstract public String getPassword();

    abstract public LocalDateTime getCreationTime();

    abstract public int getUserID();
}
