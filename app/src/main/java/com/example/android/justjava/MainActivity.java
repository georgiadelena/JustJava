/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calculates the price of the order.
     *
     * @param chocolate did the user ask for chocolate topping?
     * @param cream did the user ask for whipped cream topping?
     * @return total price
     */
    private int calculatePrice(boolean cream, boolean chocolate) {
        int total = 2;
        if(cream){
            total += 2;
        }
        if(chocolate){
            total++;
        }
        return total*quantity;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity", "Add whipped cream: " + hasWhippedCream);

        CheckBox hasChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = hasChocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        EditText insertName = (EditText) findViewById(R.id.name_view);
        String name = insertName.getText().toString();

        String message = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(message);
    }


    /**
    *This method creates a summary of the order
     *
     * @param price of the order
     * @param whippedCream true or false for the order
     *
     */
    public String createOrderSummary(int price, boolean whippedCream, boolean chocolate, String name){
        String message = getString(R.string.order_summary_name, name);
        message += "\n" + getString(R.string.add_cream) + " " + whippedCream;
        message += "\n" + getString(R.string.add_chocolate)+ " " + chocolate;
        message += "\n" + getString(R.string.quantity) + ": " + quantity;
        message += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        message += "\n" + getString(R.string.thank_you);
        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    /*
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Show an error message as a toast
            Toast.makeText(this, getString(R.string.no_more_than100)+ ".", Toast.LENGTH_SHORT).show();
            //now exit, nothing more to do here
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view){
        if (quantity == 1) {
            //show an error message as a toast
            Toast.makeText(this, getString(R.string.no_less_than1)+ ".", Toast.LENGTH_SHORT).show();
            //now exit, nothing more to do here
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

}