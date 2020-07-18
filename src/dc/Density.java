package dc;

import core.math.Vec2f;
import core.math.Vec3f;
import org.joml.Vector3f;

public class Density {
    public static float Sphere(Vec3f worldPosition, Vec3f origin, float radius)
    {
        float magnitude = worldPosition.sub(origin).length();
        return magnitude - radius;
    }

    public static float Cuboid(Vec3f worldPosition, Vec3f origin, Vec3f halfDimensions)
    {
        Vec3f pos = worldPosition.sub(origin);
        Vec3f d = new Vec3f(Math.abs(pos.getX()), Math.abs(pos.getY()), Math.abs(pos.getZ())).sub(halfDimensions);
        float m = Math.max(d.getX(), Math.max(d.getY(), d.getZ()));
        return Math.min(m, d.length() > 0 ? d.length() : new Vec3f(0, 0, 0).length());
    }

    public static float FractalNoise(int octaves, float frequency, float lacunarity, float persistence, Vec2f position)
    {
        float SCALE = 1.0f / 128.0f;
        //Vec2f p = position * SCALE;
        Vec2f p = position.mul(SCALE);
        float noise = 0.0f;

        float amplitude = 1.0f;
        //p *= frequency;
        p = p.mul(frequency);

        for (int i = 0; i < octaves; i++)
        {
            noise += SimplexNoise.noise(p.getX(), p.getY()) * amplitude;
            //p *= lacunarity;
            p = p.mul(lacunarity);
            amplitude *= persistence;
        }

        // move into [0, 1] range
        return 0.5f + (0.5f * noise);
    }


    public static float Density_Func(Vec3f pos)
    {
        float MAX_HEIGHT = 10.0f;
        float noise = FractalNoise(4, 0.5343f, 2.2324f, 0.68324f, new Vec2f(pos.X, pos.Z));
        //float noise = worldPosition.getY() - Noise(worldPosition) * 8.0f -8;
        float terrain = pos.Y - (MAX_HEIGHT * noise);
        //float terrain = worldPosition.getY() - 2;

        float cube = Cuboid(pos, new Vec3f(-4.0f, 10.0f, -4.0f), new Vec3f(12.0f, 12.0f, 12.0f));
        //float sphere = Sphere(worldPosition, new Vec3f(15.0f, 2.5f, 1.0f), 16.0f);

        //return Math.max(-cube, Math.min(sphere, terrain));
        return Math.max(-cube, terrain);

//        float density = pos.getY() - Noise(pos) * 8.0f -8;
//        return density;
    }

    public static float Cuboid(Vector3f pos)
    {
        float radius = (float)64 / 8.0f;
        Vector3f local = pos.sub(new Vector3f(64 / 2, 64 / 2, 64 / 2));
        Vector3f d = new Vector3f(Math.abs(local.x), Math.abs(local.y), Math.abs(local.z)).sub(new Vector3f(radius, radius, radius));
        float m = Math.max(d.x, Math.max(d.y, d.z));
        Vector3f max = d;
        return Math.min(m, max.length());
    }

    public static float Sphere(Vector3f pos, float radius)
    {
        Vector3f origin = new Vector3f((64 - 2.0f) * 0.5f);
        return (pos.sub(origin)).lengthSquared() - radius * radius;
    }

    public static float Noise(Vec3f pos)
    {
        float r = 0.05f;
        return (float) SimplexNoise.noise(pos.getX() * r, pos.getY() * r, pos.getZ() * r);
    }
}