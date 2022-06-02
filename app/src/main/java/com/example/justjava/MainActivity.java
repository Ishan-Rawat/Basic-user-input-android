/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
/**
 *Some notes:
 * ==> WHAT IS AN ACTIVITY:
 * An activity provides the window in which the app draws its UI.
 * Each activity is supposed to focus on one particular task ot topic.
 * the main activity for our app is stored in the file MainActivity.java
 *
 * here, in the button view in activity_main.xml, we added an onClick attribute that names which method is to be called
 * when the button is clicked, here it calls the submitOrder method which then calls the display method
 */
package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * An android app is made up of 2 things: Resources(res directory) and java code(java directory)
 * Resources contain things like texts, layouts, images, fonts, etc)
 * and java code handles things like what to do when a button is pressed, etc.
 * This division helps when we want to change the front-end without changing the back end.
 *
 * HOW ARE RESOURCES ACCESSED?
 * when an android app is compiled a tool called aapt creates a java class called R. This contains the resource IDs for all
 * the resources contained in the res directory.
 * The resource IDs have the following format:
 * For java code: R.resource_type.resource_name (for example R.string.hello)
 * For XML code: @resource_type/resource_name (for ex: @string.hello)
 *
 * HOW DO XML AND JAVA WORK TOGETHER?
 * A lot of the initialisation work for the android app is handles automatically by android. What we need to pay attention to is:
 * The MainActivity.java has a method called onCreate which is called when the MainActivity is initialised which calls another method
 * which sets the views for this main activity. This method is passed the activity_main.xml resource as a =n argument. Then, this XML
 * file is parsed, and then the view hierarchy is created(which is just a hierarchy of java objects)
 */

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URLEncoder;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity=2;

    /**
     * A NOTE ON INTENTS:
     * Sending intents:
     * Asking another app to do something
     * An intent allows you to start an activity in another app by describing a simple action you'd like to perform
     *
     * an intent is made up of:
     * 1. Action
     * 2. Data
     *
     * Data is sent in a format called URI(Unified Resource Identifier)
     */
//    ACTION_SENDTO just ain't working!!!
//    public void composeEmail(String[] addresses, String subject) {
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox); // if I put this line anywhere outside this function, then the app effin crashes. gotta debug
        /**
         * in the above line of code we are storing an abject of Checkbox class in a variable that stores such objects
         * the method findViewById returns objects of type View, Checkbox is a subclass of the View class, thus it is casted to the type Checkbox
         * just like how we can write int pi = (int) M_PI; where M_PI is a float value
         */
        boolean status = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.Chocolate_checkbox);
        boolean chocoStatus= chocolateCheckbox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String Name = nameField.getText().toString();
        //why does this work but the code in google devs page doesn't
        //plus there are some problems with the output
        String uriText = "mailto:someone@example.com" +
                "?subject=" + URLEncoder.encode("Subject") +
                "&body=" + URLEncoder.encode("some text here");
        Uri uri = Uri.parse(uriText);
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        startActivity(Intent.createChooser(sendIntent, "Send Email"));

        displayMessage(generateOrderSummary(calculatePrice(status, chocoStatus), status, chocoStatus, Name));
    }
    //not making any notes for localisation. Revisit udacity course when required.

    // This is the method for the increment button
    public void increment(View view){
        if (quantity>99){
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }
    //This method is for the decrement button
    public void decrement(View view){
        if (quantity<1){
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    private int calculatePrice(boolean whippedStatus, boolean chocoStatus){
        //whippedCream costs $2
        //choco costs $1
        int basePrice= 5;
        if (whippedStatus)basePrice+=2;
        if (chocoStatus)basePrice+=1;
        return (quantity*basePrice);
    }


    private String generateOrderSummary(int price, boolean whippedCreamStatus, boolean chocolateStatus, String name){
        String message = "Name: " + name;
        message += "\nQuantity: "+ quantity;
        message += "\n add whipped cream?: " + whippedCreamStatus;
        message += "\n add chocolate?: " + chocolateStatus;
        message += "\nPrice: $" + price;
        message += "\nThank You!";
        return message;
    }
}
