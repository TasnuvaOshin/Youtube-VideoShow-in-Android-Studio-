package net.skillbooster.projectone;

public class PerItemVariable {
    String imageurl;
    String  heading;
    String  videourl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public  PerItemVariable(String imageurl, String heading, String videourl) {
        this.imageurl = imageurl;
        this.heading = heading;
        this.videourl = videourl;
    }

    public PerItemVariable() {
    }

}
