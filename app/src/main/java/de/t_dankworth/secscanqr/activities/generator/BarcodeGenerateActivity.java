package de.t_dankworth.secscanqr.activities.generator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.MultiFormatWriter;


import de.t_dankworth.secscanqr.R;
import de.t_dankworth.secscanqr.activities.MainActivity;


/**
 * Created by Thore Dankworth
 * Last Update: 17.01.2019
 * Last Update by Thore Dankworth
 *
 * This class is all about the value to BARCODE Generate Activity. In this Class the functionality of generating a BARCODE Picture is covered.
 */

public class BarcodeGenerateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText text;
    int format;
    String text2Barcode;
    MultiFormatWriter multiFormatWriter;
    Bitmap bitmap;
    final Activity activity = this;
    private static final String STATE_TEXT = MainActivity.class.getName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_barcode_generate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        text = (EditText) findViewById(R.id.tfBarcode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setup the Spinner Menu for the different formats
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.barcode_formats_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //If the device were rotated then restore information
        if (savedInstanceState != null) {
            text2Barcode = (String) savedInstanceState.get(STATE_TEXT);
            text.setText(text2Barcode);
        }

        // Get intent, action and MINE type and check if the intent was started by a share to modul from an other app
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if ((Intent.ACTION_SEND.equals(action) && type != null) && ("text/plain".equals(type))) {
            handleSendText(intent); //call method to handle sended text
        }

        //OnClickListener for the "+" Button and functionality
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                text2Barcode = text.getText().toString().trim();
                if (text2Barcode.equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_text_first), Toast.LENGTH_SHORT).show();
                } else {
                    openResultActivity();
                }

            }
        });
    }

    /**
     * This method handles Text that was shared by an other app to SecScanQR and generates a qr code
     * @param intent from Share to from other Apps
     */
    private void handleSendText(final Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            text.setText(sharedText);
            text2Barcode = sharedText;
        }
    }

    /**
     * This method saves all data before the Activity will be destroyed
     */
    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(STATE_TEXT, text2Barcode);
    }

    /**
     * Generates the chosen format from the spinner menu
     */
    @Override
    public void onItemSelected(final AdapterView<?> adapterView, final View view, final int position, final long l) {
        String compare = adapterView.getItemAtPosition(position).toString();
        switch (compare) {
            case "BARCODE":
                format = 1;
                break;
            case "CODE_128":
                format = 2;
                break;
            case "CODE_39":
                format = 3;
                break;
            case "EAN_13":
                format = 4;
                break;
            case "EAN_8":
                format = 5;
                break;
            case "ITF":
                format = 5;
                break;
            case "PDF_417":
                format = 6;
                break;
            case "UPC_A":
                format = 7;
                break;
            default:
                format = 1;
                break;
        }
    }

    @Override
    public void onNothingSelected(final AdapterView<?> adapterView) {
        format = 1;
    }

    /**
     *  This method will launch a new Activity were the generated QR-Code will be displayed.
     */
    private void openResultActivity() {
        Intent intent = new Intent(this, GeneratorResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("CODE", text2Barcode);
        bundle.putInt("FORMAT", format);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
