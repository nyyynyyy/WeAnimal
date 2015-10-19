package jsc.cactus.com.weanimal.g_animal.main.animal.status;

/**
 * Created by INSI on 15. 9. 27..
 */
public enum StatusType {
    FOOD, WATER, LOVE;

    public String toKoreanString(){
        return super.equals(FOOD)?"먹이":super.equals(WATER)?"물":"애정";
    }
}
