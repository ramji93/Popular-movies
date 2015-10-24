package udacity.popularmovies;

/**
 * Created by e on 24-09-2015.
 */
public class Movieinfo {

        private String imageurl;
        private String moviename;
        private String synopsis;
        private double rating;
        private String releasedate;

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getMoviename() {
            return moviename;
        }

        public void setMoviename(String moviename) {
            this.moviename = moviename;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getReleasedate() {
            return releasedate;
        }

        public void setReleasedate(String releasedate) {
            this.releasedate = releasedate;
        }
    }


