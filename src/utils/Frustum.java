package utils;

import core.math.Matrix4f;
import core.math.Vec3f;

public class Frustum {
    private float[][] frustum = new float[6][4];
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int BOTTOM = 2;
    public static final int TOP = 3;
    public static final int BACK = 4;
    public static final int FRONT = 5;
    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;
    public static final int D = 3;
    private static Frustum instance = new Frustum();

    //private FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
    //private FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
    //private FloatBuffer _clip = BufferUtils.createFloatBuffer(16);

    private static float[] proj, modl, clip = new float[16];

    public static Frustum getFrustum(boolean updateFrustum, Matrix4f projection, Matrix4f view) {
        proj = projection.contTo1dFloat();
        modl = view.contTo1dFloat();
        clip = new float[16];
        if (updateFrustum) {
            instance.calculateFrustum();
        }
        return instance;
    }

    public void normalizePlane(float[][] frustum, int side) {
        float magnitude = (float) Math.sqrt(frustum[side][A] * frustum[side][A] + frustum[side][B] * frustum[side][B] + frustum[side][C] * frustum[side][C]);
        frustum[side][A] /= magnitude;
        frustum[side][B] /= magnitude;
        frustum[side][C] /= magnitude;
        frustum[side][D] /= magnitude;
    }

    private void calculateFrustum() {
        clip[ 0] = modl[ 0] * proj[ 0] + modl[ 1] * proj[ 4] + modl[ 2] * proj[ 8] + modl[ 3] * proj[12];
        clip[ 1] = modl[ 0] * proj[ 1] + modl[ 1] * proj[ 5] + modl[ 2] * proj[ 9] + modl[ 3] * proj[13];
        clip[ 2] = modl[ 0] * proj[ 2] + modl[ 1] * proj[ 6] + modl[ 2] * proj[10] + modl[ 3] * proj[14];
        clip[ 3] = modl[ 0] * proj[ 3] + modl[ 1] * proj[ 7] + modl[ 2] * proj[11] + modl[ 3] * proj[15];

        clip[ 4] = modl[ 4] * proj[ 0] + modl[ 5] * proj[ 4] + modl[ 6] * proj[ 8] + modl[ 7] * proj[12];
        clip[ 5] = modl[ 4] * proj[ 1] + modl[ 5] * proj[ 5] + modl[ 6] * proj[ 9] + modl[ 7] * proj[13];
        clip[ 6] = modl[ 4] * proj[ 2] + modl[ 5] * proj[ 6] + modl[ 6] * proj[10] + modl[ 7] * proj[14];
        clip[ 7] = modl[ 4] * proj[ 3] + modl[ 5] * proj[ 7] + modl[ 6] * proj[11] + modl[ 7] * proj[15];

        clip[ 8] = modl[ 8] * proj[ 0] + modl[ 9] * proj[ 4] + modl[10] * proj[ 8] + modl[11] * proj[12];
        clip[ 9] = modl[ 8] * proj[ 1] + modl[ 9] * proj[ 5] + modl[10] * proj[ 9] + modl[11] * proj[13];
        clip[10] = modl[ 8] * proj[ 2] + modl[ 9] * proj[ 6] + modl[10] * proj[10] + modl[11] * proj[14];
        clip[11] = modl[ 8] * proj[ 3] + modl[ 9] * proj[ 7] + modl[10] * proj[11] + modl[11] * proj[15];

        clip[12] = modl[12] * proj[ 0] + modl[13] * proj[ 4] + modl[14] * proj[ 8] + modl[15] * proj[12];
        clip[13] = modl[12] * proj[ 1] + modl[13] * proj[ 5] + modl[14] * proj[ 9] + modl[15] * proj[13];
        clip[14] = modl[12] * proj[ 2] + modl[13] * proj[ 6] + modl[14] * proj[10] + modl[15] * proj[14];
        clip[15] = modl[12] * proj[ 3] + modl[13] * proj[ 7] + modl[14] * proj[11] + modl[15] * proj[15];

        // This will extract the LEFT side of the frustum
        frustum[LEFT][A] = clip[ 3] + clip[ 0];
        frustum[LEFT][B] = clip[ 7] + clip[ 4];
        frustum[LEFT][C] = clip[11] + clip[ 8];
        frustum[LEFT][D] = clip[15] + clip[12];
        normalizePlane(frustum, LEFT);

        // This will extract the RIGHT side of the frustum
        frustum[RIGHT][A] = clip[ 3] - clip[ 0];
        frustum[RIGHT][B] = clip[ 7] - clip[ 4];
        frustum[RIGHT][C] = clip[11] - clip[ 8];
        frustum[RIGHT][D] = clip[15] - clip[12];
        normalizePlane(frustum, RIGHT);

        // This will extract the BOTTOM side of the frustum
        frustum[BOTTOM][A] = clip[ 3] + clip[ 1];
        frustum[BOTTOM][B] = clip[ 7] + clip[ 5];
        frustum[BOTTOM][C] = clip[11] + clip[ 9];
        frustum[BOTTOM][D] = clip[15] + clip[13];
        normalizePlane(frustum, BOTTOM);

        // This will extract the TOP side of the frustum
        frustum[TOP][A] = clip[ 3] - clip[ 1];
        frustum[TOP][B] = clip[ 7] - clip[ 5];
        frustum[TOP][C] = clip[11] - clip[ 9];
        frustum[TOP][D] = clip[15] - clip[13];
        normalizePlane(frustum, TOP);

        // This will extract the FRONT side of the frustum
        frustum[FRONT][A] = clip[ 3] + clip[ 2];
        frustum[FRONT][B] = clip[ 7] + clip[ 6];
        frustum[FRONT][C] = clip[11] + clip[10];
        frustum[FRONT][D] = clip[15] + clip[14];
        normalizePlane(frustum, FRONT);

        // This will extract the BACK side of the frustum
        frustum[BACK][A] = clip[ 3] - clip[ 2];
        frustum[BACK][B] = clip[ 7] - clip[ 6];
        frustum[BACK][C] = clip[11] - clip[10];
        frustum[BACK][D] = clip[15] - clip[14];
        normalizePlane(frustum, BACK);
    }

    public boolean pointInFrustum(float x, float y, float z) {
        for (int i = 0; i < 6; i++) {
            if (frustum[i][0] * x + frustum[i][1] * y + frustum[i][2] * z + frustum[i][3] <= 0.0F) {
                return false;
            }
        }

        return true;
    }

    public boolean sphereInFrustum(float x, float y, float z, float radius) {
        for (int i = 0; i < 6; i++) {
            if (frustum[i][0] * x + frustum[i][1] * y + frustum[i][2] * z + frustum[i][3] <= -radius) {
                return false;
            }
        }

        return true;
    }

    public boolean cubeFullyInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
        for (int i = 0; i < 6; i++) {
            if (frustum[i][0] * x1 + frustum[i][1] * y1 + frustum[i][2] * z1 + frustum[i][3] <= 0.0F)
                return false;
            if (frustum[i][0] * x2 + frustum[i][1] * y1 + frustum[i][2] * z1 + frustum[i][3] <= 0.0F)
                return false;
            if (frustum[i][0] * x1 + frustum[i][1] * y2 + frustum[i][2] * z1 + frustum[i][3] <= 0.0F)
                return false;
            if (frustum[i][0] * x2 + frustum[i][1] * y2 + frustum[i][2] * z1 + frustum[i][3] <= 0.0F)
                return false;
            if (frustum[i][0] * x1 + frustum[i][1] * y1 + frustum[i][2] * z2 + frustum[i][3] <= 0.0F)
                return false;
            if (frustum[i][0] * x2 + frustum[i][1] * y1 + frustum[i][2] * z2 + frustum[i][3] <= 0.0F)
                return false;
            if (frustum[i][0] * x1 + frustum[i][1] * y2 + frustum[i][2] * z2 + frustum[i][3] <= 0.0F)
                return false;
            if (frustum[i][0] * x2 + frustum[i][1] * y2 + frustum[i][2] * z2 + frustum[i][3] <= 0.0F)
                return false;
        }

        return true;
    }

    public boolean cubeInFrustum(float x, float y, float z, float size) {
        for(int i = 0; i < 6; i++ ) {
            if(frustum[i][A] * (x - size) + frustum[i][B] * (y - size) + frustum[i][C] * (z - size) + frustum[i][D] > 0)
                continue;
            if(frustum[i][A] * (x + size) + frustum[i][B] * (y - size) + frustum[i][C] * (z - size) + frustum[i][D] > 0)
                continue;
            if(frustum[i][A] * (x - size) + frustum[i][B] * (y + size) + frustum[i][C] * (z - size) + frustum[i][D] > 0)
                continue;
            if(frustum[i][A] * (x + size) + frustum[i][B] * (y + size) + frustum[i][C] * (z - size) + frustum[i][D] > 0)
                continue;
            if(frustum[i][A] * (x - size) + frustum[i][B] * (y - size) + frustum[i][C] * (z + size) + frustum[i][D] > 0)
                continue;
            if(frustum[i][A] * (x + size) + frustum[i][B] * (y - size) + frustum[i][C] * (z + size) + frustum[i][D] > 0)
                continue;
            if(frustum[i][A] * (x - size) + frustum[i][B] * (y + size) + frustum[i][C] * (z + size) + frustum[i][D] > 0)
                continue;
            if(frustum[i][A] * (x + size) + frustum[i][B] * (y + size) + frustum[i][C] * (z + size) + frustum[i][D] > 0)
                continue;

            return false;
        }

        return true;
    }

    public boolean cubeInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
        for (int i = 0; i < 6; i++) {
            if ((frustum[i][0] * x1 + frustum[i][1] * y1 + frustum[i][2] * z1 + frustum[i][3] <= 0.0F) && (frustum[i][0] * x2 + frustum[i][1] * y1 + frustum[i][2] * z1 + frustum[i][3] <= 0.0F) && (frustum[i][0] * x1 + frustum[i][1] * y2 + frustum[i][2] * z1 + frustum[i][3] <= 0.0F) && (frustum[i][0] * x2 + frustum[i][1] * y2 + frustum[i][2] * z1 + frustum[i][3] <= 0.0F) && (frustum[i][0] * x1 + frustum[i][1] * y1 + frustum[i][2] * z2 + frustum[i][3] <= 0.0F) && (frustum[i][0] * x2 + frustum[i][1] * y1 + frustum[i][2] * z2 + frustum[i][3] <= 0.0F) && (frustum[i][0] * x1 + frustum[i][1] * y2 + frustum[i][2] * z2 + frustum[i][3] <= 0.0F) && (frustum[i][0] * x2 + frustum[i][1] * y2 + frustum[i][2] * z2 + frustum[i][3] <= 0.0F)) {
                return false;
            }
        }
        return true;
    }

    public boolean nodeInFrustum(Vec3f min, int size){
        return cubeInFrustum(min.X,min.Y,min.Z,min.X+size,min.Y+size,min.Z+size);
    }
}
