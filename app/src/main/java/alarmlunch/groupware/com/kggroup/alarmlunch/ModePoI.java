package alarmlunch.groupware.com.kggroup.alarmlunch;

/**
 * Created by itsm02 on 2016. 10. 14..
 */

public class ModePoI {

    public String title;
    public String category;
    public String description;
    public String tel;
    public String addr;
    public String mapX, mapY;


    @Override
    public String toString() {
        return "ModePoI{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", tel='" + tel + '\'' +
                ", addr='" + addr + '\'' +
                ", mapX='" + mapX + '\'' +
                ", mapY='" + mapY + '\'' +
                '}';
    }
}
