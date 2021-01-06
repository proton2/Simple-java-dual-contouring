package simpleDualContouring;

import core.math.Vec3f;
import core.model.Vertex;

public class MeshVertex extends Vertex {
    public MeshVertex(Vec3f pos, Vec3f norm)
    {
        super();
        this.setPos(pos);
        this.setNormal(norm);
    }
}
