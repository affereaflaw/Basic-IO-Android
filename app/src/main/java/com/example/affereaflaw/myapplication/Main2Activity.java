package com.example.affereaflaw.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    int au = 0;
    String Hadir, Hadir1, Hadir2, Hadir3, Hadir4, Hadir5, Hadir6, Hadir7;
    ImageButton about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        about = (ImageButton) findViewById(R.id.button3);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        });
    }

    public void decrement(View view) {
        if (au==0){
            return;
        }
        au=au-1;
        display(au);
    }

    public void increment(View view) {
        if (au==8){
            return;
        }
        au=au+1;
        display(au);
    }
    /*
    Menampilkan Laporan
    */
    public void submitOrder(View view) {
        EditText text = (EditText)findViewById(R.id.Tanggal);
        String tgl  =  text.getText().toString();

        CheckBox Nama = (CheckBox) findViewById(R.id.checkBox1);
        boolean Name1 = Nama.isChecked();
        CheckBox Nama1 = (CheckBox) findViewById(R.id.checkBox2);
        boolean Name2 = Nama1.isChecked();
        CheckBox Nama2 = (CheckBox) findViewById(R.id.checkBox3);
        boolean Name3 = Nama2.isChecked();
        CheckBox Nama3 = (CheckBox) findViewById(R.id.checkBox4);
        boolean Name4 = Nama3.isChecked();
        CheckBox Nama4 = (CheckBox) findViewById(R.id.checkBox5);
        boolean Name5 = Nama4.isChecked();
        CheckBox Nama5 = (CheckBox) findViewById(R.id.checkBox6);
        boolean Name6 = Nama5.isChecked();
        CheckBox Nama6 = (CheckBox) findViewById(R.id.checkBox7);
        boolean Name7 = Nama6.isChecked();
        CheckBox Nama7 = (CheckBox) findViewById(R.id.checkBox8);
        boolean Name8 = Nama7.isChecked();

        if (Name1) {
            Hadir = "Hadir";
        } else {
            Hadir = "";
        }
        if (Name2) {
            Hadir1 = "Hadir";
        } else {
            Hadir1 = "";
        }
        if (Name3) {
            Hadir2 = "Hadir";
        } else {
            Hadir2 = "";
        }
        if (Name4) {
            Hadir3 = "Hadir";
        } else {
            Hadir3 = "";
        }
        if (Name5) {
            Hadir4 = "Hadir";
        } else {
            Hadir4 = "";
        }
        if (Name6) {
            Hadir5 = "Hadir";
        } else {
            Hadir5 = "";
        }
        if (Name7) {
            Hadir6 = "Hadir";
        } else {
            Hadir6 = "";
        }
        if (Name8) {
            Hadir7 = "Hadir";
        } else {
            Hadir7 = "";
        }

        int price = calculatePrice(au, 8, 100);
        String priceMessage = orderSummary(tgl, Hadir, Hadir1, Hadir2, Hadir3, Hadir4, Hadir5, Hadir6, Hadir7, price);
        displayPesan(priceMessage);


    }

    public void sendEmail (View view){
        EditText text = (EditText)findViewById(R.id.Tanggal);
        String tgl  =  text.getText().toString();
        int price = calculatePrice(au, 8, 100);
        String priceMessage = orderSummary(tgl, Hadir, Hadir1, Hadir2, Hadir3, Hadir4, Hadir5, Hadir6, Hadir7, price);

        //Membuat file baru di sdcard
        String state;
        state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File Dir = Environment.getExternalStorageDirectory();
            File Root = new File(Dir.getAbsolutePath()+"/Presensi");
            if (!Root.exists()) {
                Root.mkdirs();
            }
            File file = new File(Root, "presensi.txt");
            Uri pngUri = Uri.fromFile(file);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(priceMessage.getBytes());
                fileOutputStream.close();
                Toast.makeText(getApplicationContext(), "presensi.txt saved in sdcard/Presensi", Toast.LENGTH_LONG).show();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            //Detail email yang akan dikirim
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Notulensi");
            //intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            intent.putExtra(Intent.EXTRA_STREAM, pngUri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }else {
            Toast.makeText(getApplicationContext(), "SD card not mounted", Toast.LENGTH_LONG).show();
        }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayPesan(String message) {
        TextView summary = (TextView) findViewById(R.id.summary_text_view);
        summary.setText(message);
    }

    private int calculatePrice(int au, int i, int j) {
        int jumlah = au*j/i;
        return jumlah;
    }
    // display text
    private String orderSummary (String tgl, String Hadir, String Hadir1, String Hadir2, String Hadir3, String Hadir4, String Hadir5, String Hadir6, String Hadir7,  int jumlah){
        String priceMessage ="";
        priceMessage += "Nama1 :\t " + Hadir + "\n";
        priceMessage += "Nama2 :\t " + Hadir1 + "\n";
        priceMessage += "Nama3 : " + Hadir2 + "\n";
        priceMessage += "Nama4 : " + Hadir3 + "\n";
        priceMessage += "Nama5 : " + Hadir4 + "\n";
        priceMessage += "Nama6 : " + Hadir5 + "\n";
        priceMessage += "Nama7 : " + Hadir6 + "\n";
        priceMessage += "Nama8 : " + Hadir7 + "\n\n";
        priceMessage = priceMessage + "Jumlah " + au;
        priceMessage = priceMessage + "\nPresentasi kehadiran " + jumlah + "%\n\n";
        priceMessage = priceMessage + "Notulensi\n" + tgl;
        priceMessage = priceMessage + "\n\nJazakallah";

        return priceMessage;
    }
}
