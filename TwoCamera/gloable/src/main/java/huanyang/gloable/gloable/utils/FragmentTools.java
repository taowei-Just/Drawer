package huanyang.gloable.gloable.utils;

/*
 * date: 2017/9/11 11:15
 * author: tao
 */


 
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
 
import android.view.View;


public class FragmentTools {



    public static void addFragment(Activity activity , Fragment fragment, int id, String tag) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(id, fragment, tag);
        transaction.commitAllowingStateLoss();
    }
    
    public static void replaceFragment(Activity activity , Fragment fragment, int id, String tag) {
    	FragmentManager manager = activity.getFragmentManager();
    	FragmentTransaction transaction = manager.beginTransaction();
    	transaction.replace( id, fragment, tag);
    	transaction.commitAllowingStateLoss();
    }


    public static void hideFragment(Activity activity , Fragment fragment ) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide( fragment);
        View parent = (View) fragment.getView().getParent();
        if (parent!=null) {
            parent.setVisibility(View.GONE);
        }
        transaction.commitAllowingStateLoss();

    }

    public static void removeFragment(Activity activity , Fragment fragment ) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove( fragment);
        transaction.commitAllowingStateLoss();
        
       
    }
    
    
    
    

    public static void showFragment(Activity activity , Fragment fragment) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show( fragment);
        View parent = (View) fragment.getView().getParent();
        if (parent!=null) {
            parent.setVisibility(View.VISIBLE);
        }
        transaction.commitAllowingStateLoss();
    }

    public static Fragment findFragmentByTag(Activity activity , String tag) {

        return activity.getFragmentManager().findFragmentByTag(tag);

    }
    public static Fragment findFragment(Activity activity , int id) {



        return activity.getFragmentManager().findFragmentById(id);

    }

    

}
