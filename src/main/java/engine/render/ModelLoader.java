package engine.render;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.*;

public final class ModelLoader {

    public static Mesh loadModel(String objectPath, String texturePath) {
        AIScene scene = Assimp.aiImportFile(objectPath, Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate | Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_GenSmoothNormals | Assimp.aiProcess_FlipUVs);

        if (scene == null)
            throw new RuntimeException("Failed to load model at " + objectPath);

        try {
            AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
            int vertexCount = mesh.mNumVertices();

            float[] positions = new float[vertexCount * 3];
            float[] normals = new float[vertexCount * 3];
            float[] texCoords = new float[vertexCount * 2];

            AIVector3D.Buffer vertexList = mesh.mVertices();
            AIVector3D.Buffer normalsList = mesh.mNormals();
            AIVector3D.Buffer textureCoordList = mesh.mTextureCoords(0);

            for (int i = 0; i < vertexCount; i++) {
                AIVector3D vertex = vertexList.get(i);

                positions[i * 3] = vertex.x();
                positions[i * 3 + 1] = vertex.y();
                positions[i * 3 + 2] = vertex.z();

                AIVector3D normal = normalsList.get(i);

                normals[i * 3] = normal.x();
                normals[i * 3 + 1] = normal.y();
                normals[i * 3 + 2] = normal.z();

                if(textureCoordList != null){
                    AIVector3D textureCoord = textureCoordList.get(i);
                    texCoords[i * 2] = textureCoord.x();
                    texCoords[i * 2 + 1] = textureCoord.y();
                }else {
                    texCoords[i * 2] = 0;
                    texCoords[i * 2 + 1] = 0;
                }
            }

            int faceCount = mesh.mNumFaces();
            int[] indexList = new int[faceCount * 3];
            AIFace.Buffer faces = mesh.mFaces();

            for (int i = 0; i < faceCount; i++) {
                AIFace face = faces.get(i);

                indexList[i * 3] = face.mIndices().get(0);
                indexList[i * 3 + 1] = face.mIndices().get(1);
                indexList[i * 3 + 2] = face.mIndices().get(2);
            }

            Mesh newMesh = new Mesh();

            newMesh.init(positions, texCoords, normals, indexList);

            Material material = new Material(texturePath);

            material.create();

            newMesh.setMaterial(material);

            return newMesh;
        } finally {
            Assimp.aiReleaseImport(scene);
        }
    }
}
