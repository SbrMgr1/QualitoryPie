package helpers;


public class BaseHelper {
    /**
     * Function to get function name where program control is currently reached inside
     * @return String
     */
    public static String getCurrentMethodName(Object CurrentObj){
        return CurrentObj.getClass().getEnclosingMethod().getName();
    }

}
