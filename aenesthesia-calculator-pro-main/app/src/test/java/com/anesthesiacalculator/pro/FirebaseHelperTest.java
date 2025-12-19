package com.anesthesiacalculator.pro;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.anesthesiacalculator.pro.models.Drug;
import com.anesthesiacalculator.pro.models.Surgery;
import com.anesthesiacalculator.pro.utils.FirebaseHelper;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseHelperTest {

    @Test
    public void testGetInstance() {
        // Test that we can get an instance of FirebaseHelper
        FirebaseHelper helper = FirebaseHelper.getInstance();
        assertNotNull(helper);
    }

    @Test
    public void testFirebaseHelperIsSingleton() {
        // Test that FirebaseHelper is a singleton
        FirebaseHelper helper1 = FirebaseHelper.getInstance();
        FirebaseHelper helper2 = FirebaseHelper.getInstance();
        assertSame(helper1, helper2);
    }
}