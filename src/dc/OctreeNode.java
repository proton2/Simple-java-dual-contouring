package dc;

import core.math.Vec3f;

public class OctreeNode {
    public OctreeNodeType Type;
    public Vec3f min;
    public int size;
    public OctreeNode[] children;
    public OctreeDrawInfo drawInfo;

    public OctreeNode() {
        Type = OctreeNodeType.Node_None;
        min = new Vec3f(0, 0, 0);
        size = 0;
        drawInfo = new OctreeDrawInfo();

        children = new OctreeNode[8];
        for (int i = 0; i < 8; i++) {
            children[i] = null;
        }
    }
}
