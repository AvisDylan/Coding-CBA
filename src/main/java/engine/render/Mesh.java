package engine.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {

    private int vaoId, posVboId, indexVboId, texVboId, normalsVboId;
    private int vertexCount;
    private Material material;

    public void init(float[] positions, float[] texCoords, float[] normals, int[] indices){
        FloatBuffer vertexBuffer = null;
        IntBuffer indexBuffer = null;
        FloatBuffer texCoordsBuffer = null;
        FloatBuffer normalsBuffer = null;

        try{
            vertexBuffer = MemoryUtil.memAllocFloat(positions.length);
            indexBuffer = MemoryUtil.memAllocInt(indices.length);
            texCoordsBuffer = MemoryUtil.memAllocFloat(texCoords.length);
            normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
            vertexCount = indices.length;

            vertexBuffer.put(positions).flip();
            indexBuffer.put(indices).flip();
            texCoordsBuffer.put(texCoords).flip();
            normalsBuffer.put(normals).flip();

            vaoId = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(vaoId);

            posVboId = GL30.glGenBuffers();
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, posVboId);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

            texVboId = GL30.glGenBuffers();
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, texVboId);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, texCoordsBuffer, GL15.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

            indexVboId = GL30.glGenBuffers();
            GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, indexVboId);
            GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);

            normalsVboId = GL30.glGenBuffers();
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, normalsVboId);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, normalsBuffer, GL15.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);

            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
            GL30.glBindVertexArray(0);
        }finally {
            if(vertexBuffer != null)
                MemoryUtil.memFree(vertexBuffer);

            if(indexBuffer != null)
                MemoryUtil.memFree(indexBuffer);

            if(texCoordsBuffer != null)
                MemoryUtil.memFree(texCoordsBuffer);

            if(normalsBuffer != null)
                MemoryUtil.memFree(normalsBuffer);
        }
    }

    public void render(){
        GL20.glActiveTexture(GL20.GL_TEXTURE0);
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, material.getTextureID());

        GL30.glBindVertexArray(vaoId);
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);

        GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);

        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void cleanUp(){
        GL30.glDisableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(posVboId);
        GL30.glDeleteBuffers(indexVboId);
        GL30.glDeleteBuffers(texVboId);

        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);

        material.destroy();
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getPosVboId() {
        return posVboId;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
