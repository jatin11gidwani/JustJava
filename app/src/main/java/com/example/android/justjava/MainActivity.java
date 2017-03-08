package com.example.android.justjava;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    int price;
    String priceMessage;
    int noOfCoffees = 1;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox Chocolate = (CheckBox) findViewById(R.id.checkBox_chocolate);
        boolean hasChocolate = Chocolate.isChecked();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkBox_whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        calculatePrice(noOfCoffees, hasWhippedCream, hasChocolate);
        createOrderSummary(price, hasWhippedCream, hasChocolate);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int noOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        String placeholder = getString(R.string.placeholder, noOfCoffees);
        quantityTextView.setText(placeholder);
    }


    public void increment(View view) {
        if (noOfCoffees <= 9) {
            noOfCoffees = noOfCoffees + 1;
            displayQuantity(noOfCoffees);
        } else {
            final Toast toast = Toast.makeText(getBaseContext(), "Maximum no. of Coffees are 10", Toast.LENGTH_SHORT);
            toast.show();
            new CountDownTimer(1200, 1000) {
                public void onTick(long millisUntilFinished) {
                    toast.show();
                }

                public void onFinish() {
                    toast.cancel();
                }
            }.start();

        }
    }

    public void decrement(View view) {
        if (noOfCoffees >= 2) {
            noOfCoffees = noOfCoffees - 1;
            displayQuantity(noOfCoffees);
        } else {
            final Toast toast = Toast.makeText(getBaseContext(), "Minimum no. of Coffee is 1", Toast.LENGTH_SHORT);
            toast.show();
            new CountDownTimer(1200, 1000) {
                public void onTick(long millisUntilFinished) {
                    toast.show();
                }

                public void onFinish() {
                    toast.cancel();
                }
            }.start();
        }
    }

    private void calculatePrice(int quantity, boolean a, boolean b) {

        if (a && b) {
            price = (quantity * 5) + quantity + quantity * 2;
        } else if (a) price = (quantity * 5) + quantity;
        else if (b) price = (quantity * 5) + quantity * 2;
        else price = quantity * 5;

    }

    private String createOrderSummary(int price, boolean a, boolean b) {
        String a1, b1;
        EditText name = (EditText) findViewById(R.id.name);
        String Name = name.getText().toString();
        priceMessage = "$0";
        if (Name.length() == 0) {
            priceMessage = "$0";
            final Toast toast = Toast.makeText(getBaseContext(), "Enter Your Name", Toast.LENGTH_SHORT);
            toast.show();
            new CountDownTimer(1200, 1000) {
                public void onTick(long millisUntilFinished) {
                    toast.show();
                }

                public void onFinish() {
                    toast.cancel();
                }
            }.start();
        } else {
            if (a) a1 = "Yes";
            else a1 = "No";
            if (b) b1 = "Yes";
            else b1 = "No";
            priceMessage = "Name: " + Name + "\nWhipped Cream: " + a1 + "\nChocolate: " + b1 + "\nTotal: $" + price + "\nThankYou";
        }
        displayMessage(priceMessage);

        return priceMessage;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}