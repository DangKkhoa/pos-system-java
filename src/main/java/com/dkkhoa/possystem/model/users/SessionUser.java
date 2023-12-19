package com.dkkhoa.possystem.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
//@NoArgsConstructor
@Data
public class SessionUser extends User{
    public SessionUser(int userId, String username, String fullname, boolean isAdmin, String profilePicture) {
        super();
        setUserId(userId);
        setUsername(username);
        setFullname(fullname);
        setAdmin(isAdmin);
        setProfilePicture(profilePicture);
    }
}
