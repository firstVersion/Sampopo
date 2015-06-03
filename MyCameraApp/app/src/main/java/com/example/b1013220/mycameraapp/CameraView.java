package com.example.b1013220.mycameraapp;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by b1013220 on 15/06/01.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback{



    private Camera myCamera;

    public CameraView(Context context){
        super(context);
        getHolder().addCallback(this);
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder){
        myCamera = Camera.open();
        try {
            myCamera.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        Camera.Parameters parameters = myCamera.getParameters();
        parameters.setPreviewSize(width, height);
        myCamera.setParameters(parameters);
        myCamera.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        myCamera.release();
        myCamera = null;
    }

}
