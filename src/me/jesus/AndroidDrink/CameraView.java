/**
 * @package pychupitos
 * @version   1.0
 
 * @author    Jesús Manzano Camino
 * @copyright (C) 2011 Jesús Manzano Camino (email : 
 * manzanocaminojesus@gmail.com)
 *
 * @license        GNU/GPL, see gpl.txt
 * This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 */

package me.jesus.AndroidDrink;

import java.io.File;
import java.io.IOException;
import java.util.List;



import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;

public class CameraView extends Activity implements Callback {
	SurfaceView mSurfaceView;
	SurfaceHolder mSurfaceHolder;
	Camera mCamera;
	boolean mPreviewRunning = false;
	private static final int CAMERA_RESULT = 1;
	private Uri mUri;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.camerasurface);
		mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);

		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		mSurfaceView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doTakePhotoAction();
			}
		});
	}

	private void doTakePhotoAction() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				"pic_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);

		try {
			intent.putExtra("return-data", false);
			startActivityForResult(intent, CAMERA_RESULT);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == CAMERA_RESULT) {
			// Intent intent = new Intent(this, CropImage.class);
			// here you have to pass absolute path to your file
			/*
			 * intent.putExtra("image-path", mUri.getPath());
			 * intent.putExtra("scale", true); startActivity(intent);
			 */
		}
		Intent intent = new Intent(this, CropI.class);
		// here you have to pass absolute path to your file

		intent.putExtra("image-path", mUri.getPath());
		intent.putExtra("scale", true);
		startActivity(intent);
		finish();

	}

	public void surfaceCreated(SurfaceHolder holder) {

		mCamera = Camera.open();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

		if (mPreviewRunning) {
			mCamera.stopPreview();
		}
		Camera.Parameters p = mCamera.getParameters();
		p.setPictureFormat(PixelFormat.JPEG);
		List<Camera.Size> list = p.getSupportedPreviewSizes();
		Camera.Size size = list.get(list.size() - 1);
		// p.setPreviewSize(size.width, size.height);
		// p.setPreviewSize(h, w);
		mCamera.setParameters(p);
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mCamera.startPreview();
		mPreviewRunning = true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
	}

	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

		public void onPictureTaken(byte[] imageData, Camera c) {

		}

	};

}
