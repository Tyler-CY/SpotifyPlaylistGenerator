package com.example.spottyv2.api.Serializer;

import com.example.spottyv2.Entities.Playlist;
import com.example.spottyv2.Entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonSerializer {

    /**
     * Takes in username that is unique to the currently logged-in user, and returns an instantiated User entity.
     * @param username the username of the user
     * @return the existing user in the list or new User that is instantiated
     */
    public User loggedInUserInfo(String username){
        List<User> users = readJson();
        for (User user: users){
            if (comparator(username, user.getUsername())){
                return user;
            }
        }
        return new User(username);
    }
    /**
     * Saves the User loggedInUser to the Json file.
     * @param loggedInUser User that is currently logged-in in this system.
     */
    public void saveUser(User loggedInUser){
        List<User> users = readJson();
        //compare if the user is in users
        for (User user: users) {
            if (comparator(loggedInUser.getUsername(), user.getUsername())) {
                users.remove(loggedInUser);
                break;
            }
        }
        users.add(loggedInUser);
        usersToJson(users);
    }
    /**
     * Helper method.
     * Convert a list of User users into JsonArray and write to the file.
     * @param users list of User to be written to Json file
     */
    public void usersToJson(List<User> users) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("jsonables.json").toFile(), users);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // https://stackoverflow.com/questions/13514570/jackson-best-way-writes-a-java-list-to-a-json-array
    public void playlistToJson(List<Playlist> playlists){
        final ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writeValue(Paths.get("jsonables.json").toFile(), playlists);
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    /**
     * Helper method.
     * Reads in a jsonable file that contains JsonArray of json objects that represent User entities.
     * @return a Java list of User
     */
    public List<User> readJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(Paths.get("jsonables").toFile(), new TypeReference<List<User>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper method.
     * Compare the username of the current user currentUsername and comparison user tempUsername.
     * @return true if currentUsername = tempUsername, false if they are different
     */
    public Boolean comparator (String currentUsername, String tempUsername){
        return currentUsername.equals(tempUsername);
    }

    public void SaveUser() {

    }


}


