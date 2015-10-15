package jsc.cactus.com.weanimal.g_animal.main.animal;

import android.util.Log;

import jsc.cactus.com.weanimal.R;

/**
 * Created by INSI on 2015. 10. 2..
 */
public class AnimalKind {

    private static final int CHICKEN_DEFAULT[] = {R.drawable.chicken_zero_normal, R.drawable.chicken_one_normal, R.drawable.chicken_two_normal, R.drawable.chicken_four_normal};
    private static final int CHICKEN_FLABBY[] = {R.drawable.chicken_zero_normal, R.drawable.chicken_one_soso, R.drawable.chicken_two_soso};
    private static final int CHICKEN_WEAK[] = {R.drawable.chicken_zero_normal, R.drawable.chicken_one_down, R.drawable.chicken_two_down};

//    private static final int DOG_DEFAULT[] = {};
//    private static final int DOG_FLABBY[] = {};
//    private static final int DOG_WEAK[] = {};
//
//    private static final int CAT_DEFAULT[] = {};
//    private static final int CAT_FLABBY[] = {};
//    private static final int CAT_WEAK[] = {};

    public static int getImageResource(AnimalType type, AnimalStatusType statusType, int age) {
        int get[] = {};

        switch (type) {
            default:
            //case CHICKEN:
                get = statusType == AnimalStatusType.DEFAULT ? CHICKEN_DEFAULT : statusType == AnimalStatusType.FLABBY ? CHICKEN_FLABBY : CHICKEN_WEAK;
                //break;
//            case DOG:
//                get = statusType == AnimalStatusType.DEFAULT ? DOG_DEFAULT : statusType == AnimalStatusType.FLABBY ? DOG_FLABBY : DOG_WEAK;
//                break;
//            case CAT:
//                get = statusType == AnimalStatusType.DEFAULT ? CAT_DEFAULT : statusType == AnimalStatusType.FLABBY ? CAT_FLABBY : CAT_WEAK;
//                break;
            //default:
        }

        return get[Math.min(age, get.length - 1)];
    }
}
