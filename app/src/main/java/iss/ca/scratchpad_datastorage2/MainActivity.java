package iss.ca.scratchpad_datastorage2;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button save;
    Button read;
    Bitmap bitmap;

    List<Bitmap> bitmaplist = new ArrayList<>();

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Image

        for(int i = 1; i <= 20; i++) {
            String placeholder = "a1";
            Drawable drawable = getResources().getDrawable(getResources().getIdentifier("a"+ i, "drawable", getPackageName()));
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmaplist.add(bitmap);
        }

        // Drawable drawable = getResources().getDrawable(R.drawable.mario);
        //bitmap = ((BitmapDrawable) drawable).getBitmap();

        //Save Image
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("image9Dir", Context.MODE_PRIVATE);

                for(int i = 0; i < 20; i++) {
                    String inew = String.valueOf(i+1);
                    File file = new File(directory, inew + ".jpg");
                    //Bitmap bitmap = bitmaps.get(i);

                    if (!file.exists()) {
                        Log.d("path", file.toString());
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            bitmaplist.get(i).compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        });

        //Read image
        //imageView = findViewById(R.id.imageView);
        read = findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ContextWrapper cw = new ContextWrapper(getApplicationContext());
//                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//
//                File file = new File(directory, "1" + ".jpg");
//                imageView.setImageDrawable(Drawable.createFromPath(file.toString()));
                LinearLayout layout = (LinearLayout) findViewById(R.id.parentLayout);

                TableLayout table = new TableLayout(getApplicationContext());
                table.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 2.0f));
                layout.addView(table);

                for(int i=0;i<5;i++) {
                    TableRow row = new TableRow(getApplicationContext());
                    table.addView(row);
                    for(int j=0;j<4;j++) {
                        ImageView imageView = new ImageView(getApplicationContext());

                        try {
                            ContextWrapper cw = new ContextWrapper(getApplicationContext());
                            File directory = cw.getDir("image9Dir", Context.MODE_PRIVATE);
                            if(!directory.exists() && !directory.mkdirs()) {
                                throw new IllegalStateException("Could not create directory" + directory);
                            }

                            int placeholder = i*4 + j+1;
                            File file = new File(directory, String.valueOf(placeholder) + ".jpg");
                            if(!file.exists()) {
                                throw new FileNotFoundException(String.valueOf(placeholder) + ".jpg could not be found");
                            }
                            imageView.setImageDrawable(Drawable.createFromPath(file.toString()));

                            //imageView.setImageBitmap(BitmapFactory.decodeStream(new URL(listImages.get(i*4 + j)).openConnection().getInputStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //imageView.setImageDrawable(new BitmapDrawable(getResources(), scaleDown(((BitmapDrawable)images[i*4 + j]).getBitmap(), 100, true)));
                        imageView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                        row.addView(imageView);
                    }
                }

                setContentView(layout);

            }
        });


    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }



}