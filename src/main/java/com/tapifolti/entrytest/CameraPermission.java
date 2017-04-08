package com.tapifolti.entrytest;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class CameraPermission {

    private Fragment mFragment;
    public CameraPermission(Fragment fragment) {
        mFragment = fragment;
    }
    private static final String TAG = "Emotion_Permission";
    private static final int REQUEST_APP_PERMISSION = 1;
    private static final String DIALOG = "Permission dialog";

    private boolean mCanUseCamera = true;

    public boolean getCanUseCamera() {
        return mCanUseCamera;
    }
    public void setCanUseCamera(boolean can) {
        mCanUseCamera = can;
    }

    public static class ConfirmationDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentCompat.requestPermissions(parent,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_APP_PERMISSION);
                        }
                    })
                    .create();
        }
    }

    public void requestCameraPermission() {
        if (FragmentCompat.shouldShowRequestPermissionRationale(mFragment, Manifest.permission.CAMERA) ) {
            new ConfirmationDialog().show(mFragment.getChildFragmentManager(), DIALOG);
        } else {
            FragmentCompat.requestPermissions(mFragment, new String[]{Manifest.permission.CAMERA},
                    REQUEST_APP_PERMISSION);
        }
    }

    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_APP_PERMISSION) {
            if (grantResults == null || grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult() permission denied");
                mCanUseCamera = false;
            } else {
                Log.i(TAG, "onRequestPermissionsResult() permission granted");
                mCanUseCamera = true;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean showPermissionMessage() {
        return (!mCanUseCamera &&
                ContextCompat.checkSelfPermission(mFragment.getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED &&
                !FragmentCompat.shouldShowRequestPermissionRationale(mFragment, Manifest.permission.CAMERA));
    }

    public boolean hasPermission() {
        return (ContextCompat.checkSelfPermission(mFragment.getActivity(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED);
    }

}
