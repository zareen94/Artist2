package com.example.user.artist2;

/**
 * Created by user on 24/9/2017.
 */

public class Artist {
        private String artistId;
        private String artistName;
        private String artistGenre;

        public Artist(){

        }

        public Artist(String artistId, String artistName, String artistGenre) {
            this.artistId = artistId;
            this.artistName = artistName;
            this.artistGenre = artistGenre;
        }

        public String getArtistId() {
            return artistId;
        }

        public String getArtistName() {
            return artistName;
        }

        public String getArtistGenre() {
            return artistGenre;
        }
  }