package com.andriod.weddingbells.common;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andriod.weddingbells.R;
import com.andriod.weddingbells.common.CommonUtils;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecorderDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button mSubmit;
    private Button mRecordStart;
    private Button mRecordStop;
    private Button mPlay;
    private TextView mPermissionsTextview;
    private final String TAG = "RecorderDialogFragment";
    private final int REQUEST_PERMISSIONS = 2;

    private MediaRecorder mAudioRecorder;
    private String outputFile = null;

    public RecorderDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.recorder_dialog_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar toolBar = (Toolbar) view.findViewById(R.id.recorderDialogToolbar);
        toolBar.setTitle("Record Message");
        mPermissionsTextview = (TextView) view.findViewById(R.id.recorderDialogPermissionsTv);
        mRecordStart = (Button) view.findViewById(R.id.recorderDialog_recordButton);
        mPlay = (Button) view.findViewById(R.id.recorderDialog_playdButton);
        mRecordStop = (Button) view.findViewById(R.id.recorderDialog_recordStopButton);
        mRecordStart.setOnClickListener(this);
        mPlay.setOnClickListener(this);
        mRecordStop.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recorderDialog_recordButton:

                if (mAudioRecorder == null)
                    initializeMediaPlayer();
                deleteFileIfExists();
                mRecordStart.setEnabled(false);
                mRecordStop.setEnabled(true);
                try {
                    mAudioRecorder.prepare();
                    mAudioRecorder.start();
                } catch (IllegalStateException e) {
                    Log.i(TAG, "IllegalStateException");
                } catch (IOException e) {
                    Log.i(TAG, "IOException");
                }
                break;
            case R.id.recorderDialog_recordStopButton:
                mAudioRecorder.stop();
                mAudioRecorder.release();
                mAudioRecorder = null;
                mRecordStop.setEnabled(false);
                mRecordStart.setEnabled(true);
                mPlay.setEnabled(true);

                Toast.makeText(getActivity(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                break;
            case R.id.recorderDialog_playdButton:
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(getActivity(), "Playing audio", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteFileIfExists() {
        File file = new File(outputFile);
        if (file.exists())
            file.delete();
    }

    private void initializeMediaPlayer() {
        if (!requestPermissions())
            return;
        else {
            outputFile = CommonUtils.getDirectory() + "/recording.3gp";
            mAudioRecorder = new MediaRecorder();
            mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mAudioRecorder.setOutputFile(outputFile);
        }
    }

    private boolean requestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (getActivity().checkSelfPermission(RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission Granted??");
            return true;
        }
        Log.d(TAG, "" + shouldShowRequestPermissionRationale(RECORD_AUDIO));
        if (shouldShowRequestPermissionRationale(RECORD_AUDIO) && shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
            mPermissionsTextview.setVisibility(View.VISIBLE);
            mRecordStart.setEnabled(false);
            mRecordStop.setEnabled(false);
            mPermissionsTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                @TargetApi(Build.VERSION_CODES.M)
                public void onClick(View v) {
                    requestPermissions(new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
                }
            });
        } else {
            requestPermissions(new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mRecordStart.setEnabled(true);
                mPermissionsTextview.setVisibility(View.GONE);
                outputFile = CommonUtils.getDirectory() + "/recording.3gp";
                mAudioRecorder = new MediaRecorder();
                mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                mAudioRecorder.setOutputFile(outputFile);
            } else {
                Log.i(TAG, "No permissions");
                mPermissionsTextview.setVisibility(View.VISIBLE);
                mRecordStart.setEnabled(false);
                mRecordStop.setEnabled(false);
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mAudioRecorder = null;
    }
}
