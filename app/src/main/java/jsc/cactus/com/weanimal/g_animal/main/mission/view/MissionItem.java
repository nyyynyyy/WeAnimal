package jsc.cactus.com.weanimal.g_animal.main.mission.view;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionItem {
    private Integer imageId;
    private String text;

    public MissionItem(Integer imageId, String text){
        this.imageId = imageId;
        this.text = text;
    }

    public Integer getImageId(){
        return imageId;
    }

    public String getText(){
        return text;
    }
}
