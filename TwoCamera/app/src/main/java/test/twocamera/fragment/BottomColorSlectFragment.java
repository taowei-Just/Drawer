package test.twocamera.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import test.twocamera.MainActivity;
import test.twocamera.R;
import test.twocamera.custom.DrawWitch;

/**
 * Created by Administrator on 2017-11-06.
 */

public class BottomColorSlectFragment extends Fragment {


    private View rootFragmentView;
    private ImageView ivPickUp;
    private CheckBox cbRubber;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      
        return inflater.inflate(R.layout.fragment_layout_bottom, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final MainActivity activity = (MainActivity) getActivity();

        rootFragmentView = getView();
        ivPickUp = rootFragmentView.findViewById(R.id.iv_packup_right);
        cbRubber = rootFragmentView.findViewById(R.id.cb_rubber);

        ivPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (activity!=null){
                    
                    activity.hideColorFragment();
                    
                }
            }
        });
        cbRubber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){

              activity.setDraweMode(DrawWitch.draw_rubber);

                }else {

                                    activity.setDraweMode(DrawWitch.draw_line);

                }
            }
        });
        

    }

  

}
