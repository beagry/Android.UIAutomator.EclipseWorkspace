package com.bytewerk.uap.common;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import android.support.test.uiautomator.*;

import android.graphics.Point;
import android.os.RemoteException;


/* 

copy jar file to running android emulator:
# adb -e push common-ui-automator.jar /storage/common-ui-automator.jar

run method from specific class from copied jar:
# adb -e shell uiautomator runtest /storage/common-ui-automator.jar -c com.bytewerk.uap.common.CommonCase#testBackPress


*/

@SuppressWarnings("deprecation")
public class Common extends UiAutomatorTestCase {
	
	private UiDevice mDevice;
	private final String appName = "�����";

    @SuppressWarnings("deprecation")
	public void testMethod(){

        mDevice = getUiDevice();
        
        mDevice.pressHome();
        
//        setCoordinates();
//        runApp();
//
//        mDevice.wait(Until.hasObject(By.res("ru.yandex.taxi:id/confirm")),5000);
//
//        try
//        {
//            setCoordinates();
//        }
//        catch(Exception e)
//        {
//            //�� ������� ���������� ����������
//        }
//
//        UiObject2 nextButton = mDevice.findObject(By.res("ru.yandex.taxi:id/confirm"));
//        nextButton.click();
//
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            return;
//        }
//
//        proceedParams();
//
//        setDestination();
//
//        String price = readPrice();
//        android.util.Log.d("UITUTOMATOR", "testMethod: " + price);
    }

    private void setCoordinates() {


        sendLocation(55.723454, 37.604125);
    }

    private void proceedParams() {
        UiObject2 btn;

//        mDevice.wait(Until.hasObject(By.text("���������")),2000);
        btn = mDevice.findObject(By.text("���������"));
        if (btn == null) return;

        btn.click();

//        mDevice.wait(Until.hasObject(By.text("������")),2000);
        btn = mDevice.findObject(By.text("������"));
        btn.click();

        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        btn = mDevice.findObject(By.text("���������"));
        if (btn != null)
            btn.click();
    }

    private void setDestination() {

//        mDevice.wait(Until.hasObject(By.res("ru.yandex.taxi:id/address_destination")),timeOut);
        UiObject2 btn =  mDevice.findObject(By.res("ru.yandex.taxi:id/add_destination"));
        if (btn == null)
            btn = mDevice.findObject(By.res("ru.yandex.taxi:id/address_destination"));
        btn.click();


//        mDevice.wait(Until.hasObject(By.res("ru.yandex.taxi:id/search")),3000);
//        UiObject2 textEdit =  mDevice.findObject(By.res("ru.yandex.taxi:id/search"));
//        mDevice.wait(Until.hasObject(By.clazz("android.widget.EditText")),timeOut);
        UiObject2 textEdit = mDevice.findObject(By.clazz("android.widget.EditText"));
        textEdit.click();

//        mDevice.wait(Until.hasObject(By.text("������� ������� ������.\n" +
//                "��������: ���������� 7�")),3000);

        textEdit = mDevice.findObject(By.clazz("android.widget.EditText"));
        textEdit.click();
        textEdit.legacySetText("�������� �����, 1�3");

        //Wait??
        UiObject2 suggestAddresses = mDevice.findObject(By.res("android:id/list"));
        UiObject2 address =suggestAddresses.getChildren().get(0);
        address.click();
    }

    static void sendLocation(double latitude, double longitude) {
        try {
            Socket socket = new Socket("10.0.2.2", 5554); // usually 5554
            socket.setKeepAlive(true);
            String str = "geo fix " + longitude + " " + latitude ;
            Writer w = new OutputStreamWriter(socket.getOutputStream());
            w.write(str + "\r\n");
            w.flush();
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readPrice() {
//        mDevice.wait(Until.hasObject(By.res("ru.yandex.taxi:id/price_value")),bigTimeout);
        UiObject2 priceView =  mDevice.findObject(By.res("ru.yandex.taxi:id/price_value"));
        return priceView.getText().replace("~","").replace("\u20BD","").replace(" ","");
    }

    private void runApp() {
        UiObject2 calcApp = mDevice.findObject(By.text(appName));
        calcApp.click();
    }

    public void closeApp(){

        try {
            mDevice.pressRecentApps();
        } catch (RemoteException e) {
            //nothing to do
        }

        UiObject2 app = mDevice.findObjects(By.clazz("android.widget.ImageView")).get(0);
        Point center = app.getVisibleCenter();
        mDevice.swipe(center.x, center.y, (center.x-200),center.y,10);
    }
}

