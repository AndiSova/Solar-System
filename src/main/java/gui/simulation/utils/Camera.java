package gui.simulation.utils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import java.awt.event.KeyEvent;

public class Camera {
    static float cameraAzimuth = 0.0f;
    static float cameraSpeed = 0.0f;
    static float cameraElevation = 0.0f;

    static float cameraCoordsPosx = 0.0f;
    static float cameraCoordsPosy = 0.0f;
    static float cameraCoordsPosz = -15.0f;

    static float cameraUpx = 0.0f;
    static float cameraUpy = 1.0f;
    static float cameraUpz = 0.0f;

    static float tmpX = 0.0f;
    static float tmpY = 0.0f;
    static float tmpZ = 0.0f;

    private static float[] polarToCartesian(float azimuth, float length, float altitude)
    {
        float[] result = new float[3];
        float x, y, z;

        float theta = (float)Math.toRadians(90 - azimuth);
        float tantheta = (float) Math.tan(theta);
        float radian_alt = (float)Math.toRadians(altitude);
        float cospsi = (float) Math.cos(radian_alt);

        x = (float) Math.sqrt((length * length) / (tantheta * tantheta + 1));
        z = tantheta * x;

        x = -x;

        if ((azimuth >= 180.0 && azimuth <= 360.0) || azimuth == 0.0f) {
            x = -x;
            z = -z;
        }

        y = (float) (Math.sqrt(z * z + x * x) * Math.sin(radian_alt));

        if (length < 0) {
            x = -x;
            z = -z;
            y = -y;
        }

        x = x * cospsi;
        z = z * cospsi;

        result[0] = x;
        result[1] = y;
        result[2] = z;

        return result;
    }

    public static void moveCamera() {
        float[] tmp = polarToCartesian(cameraAzimuth, cameraSpeed, cameraElevation);

        cameraCoordsPosx += tmp[0];
        cameraCoordsPosy += tmp[1];
        cameraCoordsPosz += tmp[2];
    }

    public static void aimCamera(GL2 gl, GLU glu) {
        gl.glLoadIdentity();

        float[] tmp = polarToCartesian(cameraAzimuth, 100.0f, cameraElevation);
        tmpX = tmp[0];
        tmpY = tmp[1];
        tmpZ = tmp[2];

        float[] camUp = polarToCartesian(cameraAzimuth, 100.0f, cameraElevation + 90);

        cameraUpx = camUp[0];
        cameraUpy = camUp[1];
        cameraUpz = camUp[2];

        glu.gluLookAt(cameraCoordsPosx, cameraCoordsPosy, cameraCoordsPosz,
                cameraCoordsPosx + tmp[0], cameraCoordsPosy + tmp[1],
                cameraCoordsPosz + tmp[2], cameraUpx, cameraUpy, cameraUpz);
    }

    public static void calculateEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            cameraElevation -= 2;
        }

        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            cameraElevation += 2;
        }

        if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            cameraAzimuth -= 2;
        }

        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            cameraAzimuth += 2;
        }

        float cameraMovementSpeed = 0.01f;

        if (event.getKeyCode() == KeyEvent.VK_W) {
            cameraCoordsPosx += cameraMovementSpeed * tmpX;
            cameraCoordsPosy += cameraMovementSpeed * tmpY;
            cameraCoordsPosz += cameraMovementSpeed * tmpZ;
        }

        if (event.getKeyCode() == KeyEvent.VK_S) {
            cameraCoordsPosx -= cameraMovementSpeed * tmpX;
            cameraCoordsPosy -= cameraMovementSpeed * tmpY;
            cameraCoordsPosz -= cameraMovementSpeed * tmpZ;
        }

        if (cameraAzimuth > 359)
            cameraAzimuth = 1;

        if (cameraAzimuth < 1)
            cameraAzimuth = 359;
    }
}
