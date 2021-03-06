package com.example.spottyv2.api.spotifyApi;


import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Locale;

import static com.example.spottyv2.api.spotifyApi.SpotifyAuthController.spotifyApi;


@RestController
@RequestMapping("/api")
public class SpotifyApiSongController {

    /**
     * Getter method to get the top 10 songs from the currently logged-in user's library
     * @return Array of spotify track objects.
     */

    @GetMapping(value = "user-top-songs")
    public Track[] getUserTopTracks() {

        final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                .time_range("medium_term")
                .limit(10)
                .offset(5)
                .build();

        try {

            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            return trackPaging.getItems();
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
        }
        return new Track[0];
    }

    /**
     * Searches spotify for song titled songName
     * @param songName String snippet read off from the user's input.
     * @return SpotifyApi track object if song with given title was found, null if song doesn't exist in database.
     */

    @GetMapping(value = "search-song")
    public static Track searchSong(String songName){
        String capitalizedSongName = toTitleCase(songName).toLowerCase();
        try{
            int offset_temp = 1;
            while(offset_temp < 5000) {
                final SearchTracksRequest searchTracksRequest =
                        spotifyApi.searchTracks("\"" + capitalizedSongName + "\"")
                                .market(CountryCode.CA)
                                .limit(50)
                                .offset(offset_temp)
                                .build();
                final Paging<Track> trackPaging = searchTracksRequest.execute();
                Track[] found_tracks = trackPaging.getItems();
                for(Track i:found_tracks){
                    System.out.println(i.getName());
                    if((i.getName().toLowerCase()).equals(capitalizedSongName)){
                        System.out.println("Playlist generated.");
                        System.out.println(" ");
                        return i;
                    }
                }
                offset_temp += 50;
                System.out.println("check");
                //return trackPaging.getItems()[0];
            }
        } catch (IOException | SpotifyWebApiException | ParseException e){
            System.out.print("Error: " + e.getMessage());
        }
        return null;

    }

    /**
     * Helper to convert strings to title case to make search in spotify database more accurate.
     * @param input String to be converted to title case
     * @return String converted to title case
     */
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }



}
