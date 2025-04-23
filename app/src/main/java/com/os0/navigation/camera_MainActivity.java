package com.os0.navigation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class camera_MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    // Defining Permission codes.
    // We can give any value
    // but unique for each permission.
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    private Activity activity;

    private static int VIDEO_RECORD_CODE = 101;
    private Uri videoPath;

    String lastMediaFilePath = "";
    Uri lastFileUri;
    Camera camera;
    FrameLayout frameLayout;
    camera_ShowCamera showCamera;
    private RelativeLayout closeBtn;
    private RelativeLayout captureBtn;
    private RelativeLayout flashBtn;
    private RelativeLayout flipBtn;
    private RelativeLayout videoBtn;
    private TextView minusBtn;
    private TextView plusBtn;
    private boolean flashmode = false;
    private int cameraId;

    private LinearLayout relativeLayoutCaptureMediaMainZoomWrapper;
    private SeekBar seekBarCaptureMediaCameraMainZoom;
    private int maxZoom;

    private ImageView thumbnailImgView;
    public static final String SHARED_PREFS = "shared preferences";
    public SharedPreferences sharedPreferences;
    ArrayList<camera_MediaObject> mediaObjects;

    String currentImagePath = null;

    String realFilePath;

    File video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        activity = this;
        setContentView(R.layout.camera_activity_main);

        // request the user to give permissions for camera and storage
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        if(isCameraPresentInPhone()) {
            Log.i("VIDEO_RECORD_TAG", "Camera is detected");
            checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        }
        else {
            Log.i("VIDEO_RECORD_TAG", "No camera detected");
        }

        cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        closeBtn = (RelativeLayout) findViewById(R.id.closeBtn);
        captureBtn = (RelativeLayout) findViewById(R.id.captureBtn);
        flashBtn = (RelativeLayout) findViewById(R.id.flashBtn);
        flipBtn = (RelativeLayout) findViewById(R.id.flipBtn);
        videoBtn = (RelativeLayout) findViewById(R.id.videoBtn);
        minusBtn = (TextView) findViewById(R.id.minusBtn);
        plusBtn = (TextView) findViewById(R.id.plusBtn);
        seekBarCaptureMediaCameraMainZoom = (SeekBar) findViewById(R.id.seekBar);
        relativeLayoutCaptureMediaMainZoomWrapper = (LinearLayout)findViewById(R.id.linear5);
        thumbnailImgView = (ImageView) findViewById(R.id.thumbnailImgView);
        closeBtn.setOnClickListener(this);
        captureBtn.setOnClickListener(this);
        flashBtn.setOnClickListener(this);
        flipBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);
        plusBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);
        plusBtn.setOnClickListener(this);
        thumbnailImgView.setOnClickListener(this);

        seekBarCaptureMediaCameraMainZoom.setOnSeekBarChangeListener(this);

        mediaObjects = camera_Common.loadData(this);
        if(mediaObjects.size() > 0) {
            camera_MediaObject lastMedia = mediaObjects.get(mediaObjects.size() - 1);
            thumbnailImgView.setImageURI(Uri.parse(lastMedia.getMediaFilePath()));
        }
        // open the camera
        camera = Camera.open(cameraId);
        setZoomIfAvailable((Camera.Parameters)camera.getParameters());
        showCamera = new camera_ShowCamera(this, camera);
        frameLayout.addView(showCamera);
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(camera_MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(camera_MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(camera_MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(camera_MainActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(camera_MainActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(camera_MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(camera_MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            File picture_file = null;
            try {
                picture_file = getImageFile(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeFile(picture_file, bytes);
            Bitmap bitmap = decodeFile(picture_file);
            camera.startPreview();
            if(bitmap != null) {
                thumbnailImgView.setImageBitmap(bitmap);
                lastFileUri = Uri.fromFile(picture_file);
                thumbnailImgView.setImageURI(lastFileUri);
                camera_MediaObject obj = new camera_MediaObject(lastFileUri.getPath(), true);
                mediaObjects.add(obj);
                camera_Common.saveData(getApplicationContext() , mediaObjects);
            }
        }
    };

    private File getImageFile(boolean isImage) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "";
        if(isImage) {
            imageName = "jpg_" + timeStamp + "_";
        }else {
            imageName = "MP4_" + timeStamp + "_";
        }
        String root = getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
        root = root + "/captured_media";

        File myDir = new File(root);
        if(!myDir.exists()) {
            myDir.mkdirs();
        }
        
        if(isImage) {
            lastMediaFilePath = root + File.separator + imageName + "." + "jpg";
            return new File(lastMediaFilePath);
        } else {
            video = File.createTempFile(imageName, ".mp4", myDir);
            lastFileUri = Uri.fromFile(video);

            lastMediaFilePath = video.getPath();
            return video;
        }
    }

    public static void writeFile(File fileToWrite, byte[] dataToWrite) {
        try (FileOutputStream outStream = new FileOutputStream(fileToWrite)) {
            outStream.write(dataToWrite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap decodeFile(File fileToWrite) {
        try (FileInputStream fis = new FileInputStream(fileToWrite.getAbsolutePath())) {
            BitmapFactory.Options bfOptions = new BitmapFactory.Options();
            bfOptions.inJustDecodeBounds = true;
            bfOptions.inSampleSize = 1;
            bfOptions.inTempStorage = new byte[48 * 1024];
            bfOptions.inJustDecodeBounds = false;

            return BitmapFactory.decodeStream(fis, null, bfOptions);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    private void flashOnButton() {
        if (camera != null) {
            try {
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(!flashmode ? Camera.Parameters.FLASH_MODE_TORCH
                        : Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(param);
                flashmode = !flashmode;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void flipCamera() {
        if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        camera.release();
        camera = Camera.open(cameraId);
        showCamera = new camera_ShowCamera(this, camera);
        frameLayout.addView(showCamera);
    }

    private boolean isCameraPresentInPhone() {
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        }
        return false;
    }

    public String getRealPathFromURI(final Uri contentURI) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            if (idx == -1) {
                return contentURI.getPath();
            }
            String rvalue = cursor.getString(idx);
            cursor.close();
            return rvalue;
        }
    }

    public Bitmap createVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(1000000);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (Exception ex) {
                // Ignore failures while cleaning up.
            }
        }
        return bitmap;
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if ( intent.resolveActivity( getPackageManager() ) != null ) {
            File videoFile = null;
            try {
                videoFile = getImageFile(false);
            } catch ( IOException e ) {
               e.printStackTrace();
            }

            if ( videoFile != null ) {
                lastFileUri = FileProvider.getUriForFile(camera_MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", videoFile);
                intent.putExtra( MediaStore.EXTRA_OUTPUT, lastFileUri );
                intent.putExtra( MediaStore.EXTRA_VIDEO_QUALITY, 1 );
                intent.putExtra( MediaStore.EXTRA_SIZE_LIMIT, 15000000L );
                intent.putExtra( MediaStore.EXTRA_DURATION_LIMIT, 6 );
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult( intent, VIDEO_RECORD_CODE );
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VIDEO_RECORD_CODE) {
            if (resultCode == RESULT_OK && data!= null) {
                realFilePath = getRealPathFromURI(Uri.fromFile(video));
                Bitmap bmThumbnail = createVideoThumbnail(realFilePath);
                camera = Camera.open(cameraId);
                showCamera = new camera_ShowCamera(this, camera);
                frameLayout.addView(showCamera);
                camera.startPreview();
                if(bmThumbnail != null) {
                    //thumbnailImgView.setImageBitmap(bmThumbnail);
//                    thumbnailImgView.setImageResource(R.mipmap);
                    //lastFileUri = Uri.fromFile(video_file);
                    camera_MediaObject obj = new camera_MediaObject(video.getPath(), false);
                    mediaObjects.add(obj);
                    camera_Common.saveData(getApplicationContext() , mediaObjects);
                }
                Log.i("VIDEO_RECORD_TAG", "Video is recorded and available at path " + videoPath);
            }else if(resultCode == RESULT_CANCELED) {
                Log.i("VIDEO_RECORD_TAG", "Recording video is cancelled");
            }else {
                Log.i("VIDEO_RECORD_TAG", "Recording video Video has got some error");
            }
        }
    }

    // for zoom in/out
    private void setZoomByTab(int zoomByTabValue){
        if (zoomByTabValue >= 0 && zoomByTabValue <= maxZoom && camera != null) {
            Camera.Parameters cameraParameters = camera.getParameters();
            cameraParameters.setZoom(zoomByTabValue);
            camera.setParameters(cameraParameters);
        }
    }

    private void increaseZoomByTab(){
        if (camera != null) {
            Camera.Parameters cameraParameters = camera.getParameters();
            int zoom = cameraParameters.getZoom();

            int oneStepZoom = maxZoom / 10;
            zoom = zoom + oneStepZoom;

            if (zoom > maxZoom) {
                zoom = maxZoom;
            }

            if (zoom >= 0 && zoom <= maxZoom) {
                cameraParameters.setZoom(zoom);
                seekBarCaptureMediaCameraMainZoom.setProgress(zoom);
                camera.setParameters(cameraParameters);
            }
        }
    }

    private void decreaseZoomByTab(){
        if (camera != null) {
            Camera.Parameters cameraParameters = camera.getParameters();
            int zoom = cameraParameters.getZoom();

            int oneStepZoom = maxZoom / 10;
            zoom = zoom - oneStepZoom;

            if (zoom < 0) {
                zoom = 0;
            }

            if (zoom <= maxZoom) {
                cameraParameters.setZoom(zoom);
                seekBarCaptureMediaCameraMainZoom.setProgress(zoom);
                camera.setParameters(cameraParameters);
            }
        }
    }

    //TODO call it
    private void setZoomIfAvailable(Camera.Parameters cameraParameters) {
        if (cameraParameters.isZoomSupported()) {
            maxZoom = cameraParameters.getMaxZoom() / 3;
            seekBarCaptureMediaCameraMainZoom.setMax(maxZoom);
            cameraParameters.setZoom(0);

            relativeLayoutCaptureMediaMainZoomWrapper.setVisibility(View.VISIBLE);
        } else {
            relativeLayoutCaptureMediaMainZoomWrapper.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == captureBtn) {
            if(camera != null) {
                camera.takePicture(null, null, mPictureCallback);
            }
        }
        else if(view == flashBtn) {
            flashOnButton();
        }
        else if(view == flipBtn) {
            flipCamera();
        }
        else if(view == videoBtn) {
            recordVideo();
        }
        else if(view == minusBtn) {
            decreaseZoomByTab();
        }
        else if(view == plusBtn) {
            increaseZoomByTab();
        }
        else if(view == thumbnailImgView) {
            Intent intent = new Intent(this, camera_GalleryActivity.class);
            startActivity(intent);
        }
        else if(view == closeBtn) {
            //TODO close the application
            finish();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b) {
            setZoomByTab(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onResume() {
        camera = Camera.open(cameraId);
        showCamera = new camera_ShowCamera(this, camera);
        frameLayout.addView(showCamera);
        mediaObjects = camera_Common.loadData(this);
        if(mediaObjects.size() > 0) {
            camera_MediaObject lastMedia = mediaObjects.get(mediaObjects.size() - 1);
            thumbnailImgView.setImageURI(Uri.parse(lastMedia.getMediaFilePath()));
        }
        super.onResume();
    }
}