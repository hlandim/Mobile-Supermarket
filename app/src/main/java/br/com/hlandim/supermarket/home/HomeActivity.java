package br.com.hlandim.supermarket.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.home.products.PruductsFragment;
import br.com.hlandim.supermarket.util.PageAnimation;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "my_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        changeFragment(new PruductsFragment(), null);
    }


    private void changeFragment(Fragment fragment, PageAnimation pageAnimation) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //Configure animation
        if (pageAnimation != null) {
            int enter = pageAnimation.getInTransition();
            int exit = pageAnimation.getOutTransition();
            if (enter > 0 && exit > 0) {
                fragmentTransaction.setCustomAnimations(enter, exit);
            }
        }

        fragmentTransaction.replace(R.id.container_home_fragment, fragment, TAG_FRAGMENT).commit();
    }
}
