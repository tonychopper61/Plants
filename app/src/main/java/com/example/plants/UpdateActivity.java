package com.example.plants;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {
    ImageView imageView;
    EditText Name, Price, Compound;
    String img="";
    Mask mask;

    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mask=getIntent().getParcelableExtra("Bar");
        imageView=findViewById(R.id.image_base);

        Name=findViewById(R.id.UpName);
        Name.setText(mask.getSpecies());
        Price=findViewById(R.id.UpPrice);
        Price.setText(Integer.toString(mask.getPrice()));
        imageView.setImageBitmap(getImgBitmap(mask.getImage()));

    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else{
            return BitmapFactory.decodeResource(UpdateActivity.this.getResources(),
                    R.drawable.plant);
        }

    }


    public void onClickChooseImage(View view)
    {
        getImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageView.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img=Base64.getEncoder().encodeToString(bytes);
            return img;
        }
        return "";
    }
    public void onUpdateClick(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Изменение")
                .setMessage("Вы уверены что хотите изменить данные?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        putUpdate(img,Name.getText().toString(),Price.getText().toString());
                        new CountDownTimer(1000, 1000) {
                            public void onFinish() {
                                Next();
                            }

                            public void onTick(long millisUntilFinished) {

                            }
                        }.start();

                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void putUpdate(String image, String  name ,String price)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/короткихас/api/Plants/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPIUpdate update = retrofit.create(RetrofitAPIUpdate.class);
        if (image == "") {
            image = null;
        }
        DataModel model = new DataModel(image, name,Integer.parseInt(price));
        Call<DataModel> call = update.updateData(mask.getID(),model);
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                Toast.makeText(UpdateActivity.this, "Запись изменена", Toast.LENGTH_SHORT).show();
                DataModel responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });
    }

    public void onDeleteClick(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Удалить")
                .setMessage("Вы уверены что хотите Удалить данные")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePlant();
                        new CountDownTimer(1000, 1000) {
                            public void onFinish() {
                                Next();
                            }

                            public void onTick(long millisUntilFinished) {

                            }
                        }.start();

                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public  void deletePlant()
    {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/короткихас/api/Plants/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPIDelete delete = retrofit.create(RetrofitAPIDelete.class);
        Call<DataModel> call = delete.deleteData(mask.getID());

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                Toast.makeText(UpdateActivity.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                DataModel responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });

    }
    public void onBackClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void Next()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
