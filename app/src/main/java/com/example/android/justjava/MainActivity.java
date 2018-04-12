package com.example.android.justjava;


import android.content.Context;
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
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText customerName = (EditText) findViewById(R.id.customer_name);
        String custName = customerName.getText().toString();
        Log.v("MainActivity", "Name of person ordering is " + custName);
        CheckBox whippedCreamCheck = findViewById(R.id.whipped_cream_check);
        boolean hasWhippedCream = whippedCreamCheck.isChecked();
        CheckBox chocolateCheck = findViewById(R.id.chocolate_check);
        boolean hasChocolate = chocolateCheck.isChecked();
        Log.v("MainActivity", "Has whipped cream " + hasWhippedCream);
        Log.v("MainActivity", "Has chocolate " + hasChocolate);
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, custName);
        String subject = "Just Java order for " + custName;
        composeEmail(subject, priceMessage);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int price = 5;
        if (whippedCream) {
            price += 1;
        }
        if (chocolate) {
            price += 2;
        }
        return quantity * price;
    }

    /**
     * Create summary of order
     *
     * @param price of the order
     * @param whippedCreamCheck is to check if the customer ordered Whipped Cream
     * @return text summary
     */

    private String createOrderSummary(int price, boolean whippedCreamCheck, boolean chocolateCheck, String custName) {
        String priceMessage = "Name: " + custName;
        priceMessage += "\nAdd whipped cream? " + whippedCreamCheck;
        priceMessage += "\nAdd chocolate? " + chocolateCheck;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /** This method is used to send order summary via email */

    public void composeEmail(String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
       // intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the plus button is clicked
     */

    public void increment(View view) {
        Context context = getApplicationContext();
        CharSequence text = "You cannot order more than 100 coffees";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        if (quantity == 100){
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked
     */

    public void decrement(View view) {
        Context context = getApplicationContext();
        CharSequence text = "You cannot order less than one coffee";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        if (quantity == 1){
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

//    private void displayPrice(int number) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//
//    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}