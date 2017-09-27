package com.tecsol.batua.user.module.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.tecsol.batua.user.module.common.util.InternetUtil;

import butterknife.ButterKnife;

/**
 * Provides some basic operations, all fragments should extend this class.
 *
 * @author Farhan Ali
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    /**
     * Binds the viewInteractor objects within the fragment.
     *
     * @param view
     */
    protected void bindViews(View view) {
        ButterKnife.bind(this, view);
    }

    /**
     * Starts an activity with a bundle set to the intent.
     *
     * @param activityClass Class<? extends Activity>
     * @param bundle Bundle
     */
    protected void startActivity(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), activityClass);

        if (bundle != null) intent.putExtras(bundle);

        startActivity(intent);
    }

    /**
     * Get the content view of fragment.
     *
     * @return
     */
    protected View getContentView() {
        return getView();
    }

    protected void showDailog(Activity activity, String title, final String message){

        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(activity);

        alertbuilder.setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            checkInternetConnection();
                            dialog.dismiss();
                        }
                    });
        AlertDialog alert = alertbuilder.create();
        alert.show();
    }

    private void checkInternetConnection(){
        if(!(InternetUtil.hasInternetConnection(getActivity().getApplicationContext()))){
            ((BaseActivity)getActivity()).showNoInternetTitleDialog(getActivity());
        }
    }

}
