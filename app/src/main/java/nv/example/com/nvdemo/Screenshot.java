package nv.example.com.nvdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Screenshot extends AppCompatActivity {


    ImageView imageViewCapture, imageViewPreview;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        imageViewPreview = (ImageView) findViewById(R.id.ImageViewPreview);
        imageViewCapture = (ImageView) findViewById(R.id.ImageViewCapture);
        imageViewCapture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bitmap = getBitmapOfView(imageViewCapture);
                imageViewPreview.setImageBitmap(bitmap);
                createImageFromBitmap(bitmap);
            }
        });

    }//onCreate Ends


    /*
     * @author : Mayur Sharma
     * This method is used to create the bitmap of the current activity
     * This method accepts any child view of the current view
     * You can even pass the parent container like RelativeLayout or LinearLayout as a param
     * @param : View v
     */
    public Bitmap getBitmapOfView(View v)
    {
        View rootview = v.getRootView();
        rootview.setDrawingCacheEnabled(true);
        Bitmap bmp = rootview.getDrawingCache();
        return bmp;
    }


    /*
     * @author : Mayur Sharma
     * This method is used to create an image file using the bitmap
     * This method accepts an object of Bitmap class
     * Currently we are passing the bitmap of the root view of current activity
     * The image file will be created by the name capturedscreen.jpg
     * @param : Bitmap bmp
     */
    public void createImageFromBitmap(Bitmap bmp)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File( Environment.getExternalStorageDirectory() +
                "/capturedscreen.jpg");
        try
        {
            shareimage(file);
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            ostream.write(bytes.toByteArray());
            ostream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void shareimage(File file)
    {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Cover Image"));
    }
}//Class Ends
