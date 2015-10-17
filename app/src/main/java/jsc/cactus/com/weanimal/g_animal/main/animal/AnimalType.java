package jsc.cactus.com.weanimal.g_animal.main.animal;

/**
 * Created by INSI on 2015. 10. 2..
 */
public enum AnimalType {
    CHICKEN, DOG, CAT;

    public static AnimalType getType(String type){
        switch (type){
            case "CHICKEN":
                return CHICKEN;
            case "DOG":
                return DOG;
            case "CAT":
                return CAT;
            default:
                return null;
        }
    }
}
