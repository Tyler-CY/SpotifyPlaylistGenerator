package UseCases;
/*
 * PlaylistGenerator takes in the string of user input and generates a playlist of songs
 */


import Entities.Playlist;
import Entities.Song;

import java.util.*;

public class PlaylistGenerator {
    public static Playlist generatePlaylist( ArrayList<String> words){
        Playlist newPlaylist = new Playlist();
        for (String s : words) {
            Song song = SongGenerator.generateSong(s); //create a song entity for that song
            // receive the song entities and instantiate a playlist entity
            newPlaylist.addSong(song);
        }
        return newPlaylist;
    }
}