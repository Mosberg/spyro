You can’t convert that COLLADA model directly inside me, but you can get clean Minecraft-ready Blockbench Java/JSON models with this workflow.

## 1. Import the DAE into Blockbench

1. Open **Blockbench → File → New → Modded Entity** (Fabric/Forge doesn’t matter yet).
2. Delete the default cube.
3. Go to **File → Import → Import OBJ/DAE** and select `Spyro.dae`.
4. When prompted:
   - Enable **Scale to fit** if the model is huge or tiny.
   - Keep **Merge vertices** on; disable animation import (you only need bind pose for entity models).
5. Confirm that:
   - The mesh is centered roughly around the origin.
   - Y is up and the model faces +Z (Minecraft convention). If not, use **Transform → Rotate** (usually ±90° around X or Y) to fix.

(If Blockbench refuses the DAE, first convert it to OBJ in Blender, then import OBJ.)

## 2. Rebuild a Minecraft-style bone hierarchy

The DAE contains a full armature with many tiny facial/wing bones that are overkill for a vanilla-style entity. [ppl-ai-file-upload.s3.amazonaws](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/9838088/c75c9d93-2925-43a9-a01d-f5f2ed8c905f/Spyro.dae)

In Blockbench’s **Outliner**:

1. Create high-level bones only:
   - `body`
   - `head`
   - `left_wing`, `right_wing`
   - `left_front_leg`, `right_front_leg`
   - `left_back_leg`, `right_back_leg`
   - `tail_base`, `tail_mid`, `tail_tip` (as needed)
2. For each anatomical chunk of the mesh:
   - Select the faces (or cubes, once you boxify it, see next section).
   - Use **Right-click → Group into bone** and assign to one of the above bones.
3. Make the hierarchy:
   - `body`
     - `head`
     - `left_wing`
     - `right_wing`
     - `left_front_leg`
     - `right_front_leg`
     - `left_back_leg`
     - `right_back_leg`
     - `tail_base`
       - `tail_mid`
         - `tail_tip`

Keep pivots where Minecraft would rotate (neck joint, hip, wing root, tail segment joints).

## 3. Replace the high-poly mesh with Minecraft cubes

Minecraft entity models must be composed of boxes; JSON can’t store arbitrary triangle meshes. [ppl-ai-file-upload.s3.amazonaws](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/images/9838088/2377301c-f214-4c34-862b-02697c94c997/T_CPS1999_Spyro_SuperCharge_E.jpg?AWSAccessKeyId=ASIA2F3EMEYE5EGD7Y7W&Signature=FLLmvOsvwHnc%2BIwcJVtIl0%2F%2FRhU%3D&x-amz-security-token=IQoJb3JpZ2luX2VjEOb%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJHMEUCIQDE4EYAEFskgMV34a769M2jojpenQhOdysqcvOCEQpLvAIgbmpqNySWCJokys05UwtR4DXdP9f1%2FA5avciAuXWyMOkq%2FAQIr%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARABGgw2OTk3NTMzMDk3MDUiDOPGvkXQRBTAWjNemCrQBCGiJEHtxexgOHyrMZeQf1fnpl6KyNKTdOWkHvx6%2BlGNGEMdrOXhOtGTQdKqvQNwfALbjjymAU5NAijxFb5tR6PDdGszp0GWaUko6QPlXeUR8TD4ET%2BiUiKJH8O2l31MIainQJdtczNQAO1EHWYSQEwGwpGCrZQClkRRkJ6YbvDl3i5aa02fzg8J2oz7vksv6rMaTa7%2Bhhxnrgsyv%2BVSJrUUdoTmEIC%2FUwtUmrUg2OFEsM1vpGXN8HdSkJOh7Ug9cIHb6IMs0o6O4rXNF%2F526uR18B8zMY2VZGfy%2FOLy3HzMZaFvZLBTb7jow%2B4swEv9rHid9J0teVNeFW1%2Bq%2BbaugWQ5pxQFoOkMygd0uZU2IRzQjhs%2BRzfiSjA%2BbbikhVQRq4nVFtrO6dLsQ%2B0hEUCbegoeDxRFU39YMYqzLoobKefOuIec3xbZEWfyHh4U7RtPxw3mA83rgoKebmiyLxYXJNjQJX3nV4RWXHTh%2BEgfqWNJZQFlu%2FDM5pEQ8Z78BS6Nkyhf%2F912JSYA19LVH7DEuxC5szvATT7Ok%2B4K8poX0N5eOq2%2FqYm%2BFaBmMvGYg96qorQdd53Z0DC2OxJphuFxDc%2BRnsZctRLOBv9qLUhzF33eW3OvBsz8sqbQJtxYgXp5ds9WS86vjA8M4UAruNdmiDj5qbFlaa6oug69BzjXU%2FFnY3QIYpUz5KFa%2FPlvY%2FFE9%2Bq92xOWeuib%2BVQ3PkZ85MPZEgLgHMz0j6W%2BbEv5705f18gIOyqwCIPiBa34Pri85%2BitwaQMZmllzBMzW7GH8AwpNKuzAY6mAEi0dW%2BgF9bXjVOZBjDoqXmQvUtmV8bVlZqfZ1D%2BuZ9VDikcA%2Bv8ayx7UzqpR%2FcCntPu5XQ5g9znL4umV65nndN0EhrQBzzspRfnUAPHWoDe5F%2BD9iY%2Fv%2Fflw35zJNnbTt945YGu0iovVYzaAfln8jv5SZIJV7nTM9%2BOYaYlPhD1e8Lvb0fBT2SPvtK67o3jTjMfKZIb1c6GA%3D%3D&Expires=1770761814)

1. Hide or lock the imported mesh layer; use it only as a visual reference.
2. For each bone:
   - Add cubes that roughly fit the body part (snout, horns, torso, limbs, wings, tail).
   - Use **Snap** and **Inflate** to keep a voxel-ish look but wrap the original silhouette.
   - Minimize cube count: fewer boxes = faster render + easier animations.
3. Delete (or keep hidden) the original mesh once the box model looks right.

This is the time‑consuming “retopology to cubes” step; there’s no way around it if you want valid Java/JSON models.

## 4. Assign your attached textures

You’ve attached body, iris, and elemental effect textures (`T_CPS1999_Spyro_Body_C.jpg`, iris, poison/ice/magma/supercharge fire etc.). [ppl-ai-file-upload.s3.amazonaws](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/images/9838088/8df50b8d-b8dc-421b-9027-c25f911e8327/T_CPS1999_Spyro_RageFlames_E.jpg?AWSAccessKeyId=ASIA2F3EMEYE5EGD7Y7W&Signature=1BUIhhbJj1nFc0NPWRlTX6hpqvo%3D&x-amz-security-token=IQoJb3JpZ2luX2VjEOb%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJHMEUCIQDE4EYAEFskgMV34a769M2jojpenQhOdysqcvOCEQpLvAIgbmpqNySWCJokys05UwtR4DXdP9f1%2FA5avciAuXWyMOkq%2FAQIr%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARABGgw2OTk3NTMzMDk3MDUiDOPGvkXQRBTAWjNemCrQBCGiJEHtxexgOHyrMZeQf1fnpl6KyNKTdOWkHvx6%2BlGNGEMdrOXhOtGTQdKqvQNwfALbjjymAU5NAijxFb5tR6PDdGszp0GWaUko6QPlXeUR8TD4ET%2BiUiKJH8O2l31MIainQJdtczNQAO1EHWYSQEwGwpGCrZQClkRRkJ6YbvDl3i5aa02fzg8J2oz7vksv6rMaTa7%2Bhhxnrgsyv%2BVSJrUUdoTmEIC%2FUwtUmrUg2OFEsM1vpGXN8HdSkJOh7Ug9cIHb6IMs0o6O4rXNF%2F526uR18B8zMY2VZGfy%2FOLy3HzMZaFvZLBTb7jow%2B4swEv9rHid9J0teVNeFW1%2Bq%2BbaugWQ5pxQFoOkMygd0uZU2IRzQjhs%2BRzfiSjA%2BbbikhVQRq4nVFtrO6dLsQ%2B0hEUCbegoeDxRFU39YMYqzLoobKefOuIec3xbZEWfyHh4U7RtPxw3mA83rgoKebmiyLxYXJNjQJX3nV4RWXHTh%2BEgfqWNJZQFlu%2FDM5pEQ8Z78BS6Nkyhf%2F912JSYA19LVH7DEuxC5szvATT7Ok%2B4K8poX0N5eOq2%2FqYm%2BFaBmMvGYg96qorQdd53Z0DC2OxJphuFxDc%2BRnsZctRLOBv9qLUhzF33eW3OvBsz8sqbQJtxYgXp5ds9WS86vjA8M4UAruNdmiDj5qbFlaa6oug69BzjXU%2FFnY3QIYpUz5KFa%2FPlvY%2FFE9%2Bq92xOWeuib%2BVQ3PkZ85MPZEgLgHMz0j6W%2BbEv5705f18gIOyqwCIPiBa34Pri85%2BitwaQMZmllzBMzW7GH8AwpNKuzAY6mAEi0dW%2BgF9bXjVOZBjDoqXmQvUtmV8bVlZqfZ1D%2BuZ9VDikcA%2Bv8ayx7UzqpR%2FcCntPu5XQ5g9znL4umV65nndN0EhrQBzzspRfnUAPHWoDe5F%2BD9iY%2Fv%2Fflw35zJNnbTt945YGu0iovVYzaAfln8jv5SZIJV7nTM9%2BOYaYlPhD1e8Lvb0fBT2SPvtK67o3jTjMfKZIb1c6GA%3D%3D&Expires=1770761814)

1. In Blockbench, go to **Paint → Textures → Import** and load:
   - `T_CPS1999_Spyro_Body_C.jpg` as the main body texture.
   - `T_CPS1155_Spyro_Iris_C.jpg` as an additional texture if you want separate eye planes.
   - The elemental textures (`…_Fireball_E`, `…_Ice_E`, `…_Magma_E`, `…_Poison_E`, `…_SuperCharge_E`) for overlay cubes / separate models.
2. For the entity model:
   - Set `T_CPS1999_Spyro_Body_C.jpg` as the model’s main texture.
   - UV-map each cube over the right part of that texture (Blockbench’s UV editor).
3. For elemental variants:
   - Either create separate models using the same geometry but different texture assignments.
   - Or create child bones with small overlay cubes and map them to the elemental textures.

## 5. Export to Java (for code) and to JSON (optional)

### For Java entity model

1. Set **File → Project → Export Version** to your target loader (Fabric/Forge) and Minecraft version.
2. Use **File → Export → Export Java Entity**:
   - Choose the generator (e.g., Mojang mappings, Forge, Fabric GeckoLib, etc.).
   - Blockbench will generate a `.java` file containing the model class with all cubes and bones. [ppl-ai-file-upload.s3.amazonaws](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/images/9838088/8df50b8d-b8dc-421b-9027-c25f911e8327/T_CPS1999_Spyro_RageFlames_E.jpg?AWSAccessKeyId=ASIA2F3EMEYE5EGD7Y7W&Signature=1BUIhhbJj1nFc0NPWRlTX6hpqvo%3D&x-amz-security-token=IQoJb3JpZ2luX2VjEOb%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJHMEUCIQDE4EYAEFskgMV34a769M2jojpenQhOdysqcvOCEQpLvAIgbmpqNySWCJokys05UwtR4DXdP9f1%2FA5avciAuXWyMOkq%2FAQIr%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARABGgw2OTk3NTMzMDk3MDUiDOPGvkXQRBTAWjNemCrQBCGiJEHtxexgOHyrMZeQf1fnpl6KyNKTdOWkHvx6%2BlGNGEMdrOXhOtGTQdKqvQNwfALbjjymAU5NAijxFb5tR6PDdGszp0GWaUko6QPlXeUR8TD4ET%2BiUiKJH8O2l31MIainQJdtczNQAO1EHWYSQEwGwpGCrZQClkRRkJ6YbvDl3i5aa02fzg8J2oz7vksv6rMaTa7%2Bhhxnrgsyv%2BVSJrUUdoTmEIC%2FUwtUmrUg2OFEsM1vpGXN8HdSkJOh7Ug9cIHb6IMs0o6O4rXNF%2F526uR18B8zMY2VZGfy%2FOLy3HzMZaFvZLBTb7jow%2B4swEv9rHid9J0teVNeFW1%2Bq%2BbaugWQ5pxQFoOkMygd0uZU2IRzQjhs%2BRzfiSjA%2BbbikhVQRq4nVFtrO6dLsQ%2B0hEUCbegoeDxRFU39YMYqzLoobKefOuIec3xbZEWfyHh4U7RtPxw3mA83rgoKebmiyLxYXJNjQJX3nV4RWXHTh%2BEgfqWNJZQFlu%2FDM5pEQ8Z78BS6Nkyhf%2F912JSYA19LVH7DEuxC5szvATT7Ok%2B4K8poX0N5eOq2%2FqYm%2BFaBmMvGYg96qorQdd53Z0DC2OxJphuFxDc%2BRnsZctRLOBv9qLUhzF33eW3OvBsz8sqbQJtxYgXp5ds9WS86vjA8M4UAruNdmiDj5qbFlaa6oug69BzjXU%2FFnY3QIYpUz5KFa%2FPlvY%2FFE9%2Bq92xOWeuib%2BVQ3PkZ85MPZEgLgHMz0j6W%2BbEv5705f18gIOyqwCIPiBa34Pri85%2BitwaQMZmllzBMzW7GH8AwpNKuzAY6mAEi0dW%2BgF9bXjVOZBjDoqXmQvUtmV8bVlZqfZ1D%2BuZ9VDikcA%2Bv8ayx7UzqpR%2FcCntPu5XQ5g9znL4umV65nndN0EhrQBzzspRfnUAPHWoDe5F%2BD9iY%2Fv%2Fflw35zJNnbTt945YGu0iovVYzaAfln8jv5SZIJV7nTM9%2BOYaYlPhD1e8Lvb0fBT2SPvtK67o3jTjMfKZIb1c6GA%3D%3D&Expires=1770761814)
3. Drop that Java file into your `client/model` package and wire it to your custom entity renderer.

### For JSON models (if you want them)

Blockbench can also export a static model as **“OptiFine format”** or generic **“Bedrock”** JSON, but Fabric entity renderers typically use Java models, not JSON. [ppl-ai-file-upload.s3.amazonaws](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/images/9838088/b82ee789-7ca0-40b3-88dd-59cb63b54836/T_CPS1999_Spyro_Fireball_E.jpg?AWSAccessKeyId=ASIA2F3EMEYE5EGD7Y7W&Signature=j4Q9Inf%2Fr2bGKwhtda9%2BKETpvts%3D&x-amz-security-token=IQoJb3JpZ2luX2VjEOb%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJHMEUCIQDE4EYAEFskgMV34a769M2jojpenQhOdysqcvOCEQpLvAIgbmpqNySWCJokys05UwtR4DXdP9f1%2FA5avciAuXWyMOkq%2FAQIr%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARABGgw2OTk3NTMzMDk3MDUiDOPGvkXQRBTAWjNemCrQBCGiJEHtxexgOHyrMZeQf1fnpl6KyNKTdOWkHvx6%2BlGNGEMdrOXhOtGTQdKqvQNwfALbjjymAU5NAijxFb5tR6PDdGszp0GWaUko6QPlXeUR8TD4ET%2BiUiKJH8O2l31MIainQJdtczNQAO1EHWYSQEwGwpGCrZQClkRRkJ6YbvDl3i5aa02fzg8J2oz7vksv6rMaTa7%2Bhhxnrgsyv%2BVSJrUUdoTmEIC%2FUwtUmrUg2OFEsM1vpGXN8HdSkJOh7Ug9cIHb6IMs0o6O4rXNF%2F526uR18B8zMY2VZGfy%2FOLy3HzMZaFvZLBTb7jow%2B4swEv9rHid9J0teVNeFW1%2Bq%2BbaugWQ5pxQFoOkMygd0uZU2IRzQjhs%2BRzfiSjA%2BbbikhVQRq4nVFtrO6dLsQ%2B0hEUCbegoeDxRFU39YMYqzLoobKefOuIec3xbZEWfyHh4U7RtPxw3mA83rgoKebmiyLxYXJNjQJX3nV4RWXHTh%2BEgfqWNJZQFlu%2FDM5pEQ8Z78BS6Nkyhf%2F912JSYA19LVH7DEuxC5szvATT7Ok%2B4K8poX0N5eOq2%2FqYm%2BFaBmMvGYg96qorQdd53Z0DC2OxJphuFxDc%2BRnsZctRLOBv9qLUhzF33eW3OvBsz8sqbQJtxYgXp5ds9WS86vjA8M4UAruNdmiDj5qbFlaa6oug69BzjXU%2FFnY3QIYpUz5KFa%2FPlvY%2FFE9%2Bq92xOWeuib%2BVQ3PkZ85MPZEgLgHMz0j6W%2BbEv5705f18gIOyqwCIPiBa34Pri85%2BitwaQMZmllzBMzW7GH8AwpNKuzAY6mAEi0dW%2BgF9bXjVOZBjDoqXmQvUtmV8bVlZqfZ1D%2BuZ9VDikcA%2Bv8ayx7UzqpR%2FcCntPu5XQ5g9znL4umV65nndN0EhrQBzzspRfnUAPHWoDe5F%2BD9iY%2Fv%2Fflw35zJNnbTt945YGu0iovVYzaAfln8jv5SZIJV7nTM9%2BOYaYlPhD1e8Lvb0fBT2SPvtK67o3jTjMfKZIb1c6GA%3D%3D&Expires=1770761814)

If you specifically need JSON:

1. Ensure your model uses only cubes, no mesh remnants.
2. Use **File → Export → Export Bedrock Geometry** to get a geometry JSON.
3. You can adapt that JSON by:
   - Removing Bedrock-specific fields.
   - Converting to whatever custom JSON format your rendering pipeline expects.
   - In many Fabric setups, this JSON is just data you parse yourself; vanilla doesn’t use it for entities.

There is no “vanilla Java Edition entity JSON” standard; so expect to either stick with Java or write your own loader. [ppl-ai-file-upload.s3.amazonaws](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/images/9838088/b82ee789-7ca0-40b3-88dd-59cb63b54836/T_CPS1999_Spyro_Fireball_E.jpg?AWSAccessKeyId=ASIA2F3EMEYE5EGD7Y7W&Signature=j4Q9Inf%2Fr2bGKwhtda9%2BKETpvts%3D&x-amz-security-token=IQoJb3JpZ2luX2VjEOb%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJHMEUCIQDE4EYAEFskgMV34a769M2jojpenQhOdysqcvOCEQpLvAIgbmpqNySWCJokys05UwtR4DXdP9f1%2FA5avciAuXWyMOkq%2FAQIr%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARABGgw2OTk3NTMzMDk3MDUiDOPGvkXQRBTAWjNemCrQBCGiJEHtxexgOHyrMZeQf1fnpl6KyNKTdOWkHvx6%2BlGNGEMdrOXhOtGTQdKqvQNwfALbjjymAU5NAijxFb5tR6PDdGszp0GWaUko6QPlXeUR8TD4ET%2BiUiKJH8O2l31MIainQJdtczNQAO1EHWYSQEwGwpGCrZQClkRRkJ6YbvDl3i5aa02fzg8J2oz7vksv6rMaTa7%2Bhhxnrgsyv%2BVSJrUUdoTmEIC%2FUwtUmrUg2OFEsM1vpGXN8HdSkJOh7Ug9cIHb6IMs0o6O4rXNF%2F526uR18B8zMY2VZGfy%2FOLy3HzMZaFvZLBTb7jow%2B4swEv9rHid9J0teVNeFW1%2Bq%2BbaugWQ5pxQFoOkMygd0uZU2IRzQjhs%2BRzfiSjA%2BbbikhVQRq4nVFtrO6dLsQ%2B0hEUCbegoeDxRFU39YMYqzLoobKefOuIec3xbZEWfyHh4U7RtPxw3mA83rgoKebmiyLxYXJNjQJX3nV4RWXHTh%2BEgfqWNJZQFlu%2FDM5pEQ8Z78BS6Nkyhf%2F912JSYA19LVH7DEuxC5szvATT7Ok%2B4K8poX0N5eOq2%2FqYm%2BFaBmMvGYg96qorQdd53Z0DC2OxJphuFxDc%2BRnsZctRLOBv9qLUhzF33eW3OvBsz8sqbQJtxYgXp5ds9WS86vjA8M4UAruNdmiDj5qbFlaa6oug69BzjXU%2FFnY3QIYpUz5KFa%2FPlvY%2FFE9%2Bq92xOWeuib%2BVQ3PkZ85MPZEgLgHMz0j6W%2BbEv5705f18gIOyqwCIPiBa34Pri85%2BitwaQMZmllzBMzW7GH8AwpNKuzAY6mAEi0dW%2BgF9bXjVOZBjDoqXmQvUtmV8bVlZqfZ1D%2BuZ9VDikcA%2Bv8ayx7UzqpR%2FcCntPu5XQ5g9znL4umV65nndN0EhrQBzzspRfnUAPHWoDe5F%2BD9iY%2Fv%2Fflw35zJNnbTt945YGu0iovVYzaAfln8jv5SZIJV7nTM9%2BOYaYlPhD1e8Lvb0fBT2SPvtK67o3jTjMfKZIb1c6GA%3D%3D&Expires=1770761814)

## 6. Practical notes with this specific Spyro rig

From the DAE:

- The armature has ~200+ joints (eyelids, lip bones, many tail/wing segments). [ppl-ai-file-upload.s3.amazonaws](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/9838088/c75c9d93-2925-43a9-a01d-f5f2ed8c905f/Spyro.dae)
- Minecraft animations are usually bone‑level only (no skin weighting / blendshapes), so ignore most micro‑bones.
- Use only macro joints (spine, neck, head, jaw if you want a bite, wings, legs, a few tail segments) and animate them in code or via GeckoLib.

## 7. If you want an automated pipeline

Because you’re comfortable with schema‑driven workflows:

- You could script a **Blender → Blockbench** pipeline where:
  - Blender Python exports simplified boxes and a bone hierarchy as a JSON schema.
  - A small converter turns that schema into Blockbench-compatible JSON and/or Java stubs.
- However, with a hero model like Spyro, you’ll still likely do one manual Cubify pass to decide the box layout.

If you tell me which exact target you want (plain Fabric Java model, GeckoLib, custom JSON‑driven renderer), I can sketch out the precise export settings and the skeleton/bone names that will play nicest with your code side.

You’ll end up with two artifacts per model: a plain Fabric Java model class, and a JSON schema your custom renderer can consume. Below is a concrete way to structure both sides.

---

## 1. Export a plain Fabric Java model

In Blockbench, use the **Modded Entity** format, then:

1. Make sure the model is all cubes with a clean bone hierarchy (body, head, wings, legs, tail).
2. Set **Filter → Presets / Export Version** to a modern version that uses `ModelPart` and `TexturedModelData` (any 1.17+ Java export is close enough; mappings differ but structure is similar). [reddit](https://www.reddit.com/r/fabricmc/comments/rsairs/how_do_i_export_a_entity_model_from_blockbench_to/)
3. Use **File → Export → Export Java** and choose the “Modded Entity” Java export. This gives you a class similar to:

```java
public class SpyroModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    // ... legs, tail, etc.

    public SpyroModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
        // ...
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData body = root.addChild("body",
            ModelPartBuilder.create()
                .uv(0, 0).cuboid(-4.0F, -6.0F, -8.0F, 8.0F, 12.0F, 16.0F),
            ModelTransform.pivot(0.0F, 12.0F, 0.0F));

        // head, wings, limbs, tail segments… (Blockbench fills these in)

        return TexturedModelData.of(modelData, 128, 128); // texture size
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance,
                          float animationProgress, float headYaw, float headPitch) {
        // vanilla-style animations; can stay simple if your JSON renderer owns complex anims
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices,
                       int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
```

You usually have to do a quick pass to:

- Fix imports (`net.minecraft.client.model.*`, `net.minecraft.client.render.entity.model.*`, etc.).
- Rename fields to your preferred style and adapt to Yarn mappings. For modern Fabric, following the base `EntityModel` + `TexturedModelData` pattern from the Fabric wiki keeps everything compatible. [wiki.fabricmc](https://wiki.fabricmc.net/tutorial:custom_model)

This Java model can be used directly by a standard `EntityRenderer` if you ever want to bypass JSON and just animate in `setAngles`.

---

## 2. Define a JSON schema for the same model

For a JSON‑driven renderer, define a minimal, stable schema approximating Bedrock geo JSON but tailored to your code. Blockbench can export a **Bedrock Geometry** JSON, which you can treat as your base. [blockbench](https://www.blockbench.net/wiki/blockbench/formats/)

Example of a trimmed, custom Spyro JSON (one file per model):

```json
{
  "format_version": "1.0",
  "model_id": "spyro",
  "texture": "modid:textures/entity/spyro/spyro_body.png",
  "texture_width": 128,
  "texture_height": 128,
  "bones": [
    {
      "name": "root",
      "pivot": [0, 0, 0],
      "cubes": [],
      "children": ["body"]
    },
    {
      "name": "body",
      "pivot": [0, 12, 0],
      "cubes": [
        {
          "uv": [0, 0],
          "size": [8, 12, 16],
          "origin": [-4, -6, -8]
        }
      ],
      "children": [
        "head",
        "left_wing",
        "right_wing",
        "tail_base",
        "left_front_leg",
        "right_front_leg",
        "left_back_leg",
        "right_back_leg"
      ]
    },
    {
      "name": "head",
      "pivot": [0, 6, -8],
      "cubes": [
        {
          "uv": [0, 28],
          "size": [6, 6, 8],
          "origin": [-3, -3, -16]
        }
      ],
      "children": []
    }
    // ... wings, legs, tail segments
  ]
}
```

Design choices:

- `origin` = Blockbench cube origin in model space.
- `pivot` = bone pivot in model space (the point rotations are applied around).
- `cubes` = one or more boxes with `size` (x, y, z) and `uv` coordinates.
- Optional extras: `mirror`, per‑cube `rotation`, or per‑bone `default_rotation` arrays.

You can export Bedrock geo JSON and map fields:

- `bones[].name`, `pivot`, `cubes[].origin`, `size`, `uv` map almost 1:1.
- Strip everything you don’t need (locators, visibility flags, materials). Bedrock geo is documented and close to what you want. [blockbench](https://www.blockbench.net/wiki/blockbench/formats/)

---

## 3. Implement the custom JSON‑driven renderer (Fabric)

At a high level:

1. Create a loader that turns your JSON into a runtime structure.
2. Create a render‑time model that builds `ModelPart` trees from that structure.
3. Apply animations by mutating per‑bone transforms before render.

### 3.1. Data classes for JSON

Example Kotlin/Java POJOs (Java here for clarity):

```java
public record SpyroModelJson(
    String model_id,
    String texture,
    int texture_width,
    int texture_height,
    List<BoneJson> bones
) {}

public record BoneJson(
    String name,
    float[] pivot,
    List<CubeJson> cubes,
    List<String> children,
    float[] default_rotation
) {}

public record CubeJson(
    float[] origin,
    float[] size,
    float[] uv,
    boolean mirror
) {}
```

Use your favorite JSON lib (Gson or Jackson) to deserialize.

### 3.2. Build a `ModelPart` tree at runtime

You can generate `ModelPart` trees without `TexturedModelData` by directly constructing `ModelPart` instances and their cuboids; Fabric’s dynamic model tutorial shows the approach for blocks, the same principle applies to entities. [wiki.fabricmc](https://wiki.fabricmc.net/tutorial:custom_model)

Pseudo‑code:

```java
public class JsonDrivenModel extends EntityModel<SpyroEntity> {
    private final Map<String, ModelPart> bones = new HashMap<>();
    private final ModelPart root;
    private final Identifier textureId;
    private final int texWidth;
    private final int texHeight;

    public JsonDrivenModel(SpyroModelJson json) {
        this.textureId = new Identifier(json.texture());
        this.texWidth = json.texture_width();
        this.texHeight = json.texture_height();

        // Create ModelPart instances first
        for (BoneJson boneJson : json.bones()) {
            ModelPart part = new ModelPart(Collections.emptyList(), new HashMap<>());
            bones.put(boneJson.name(), part);
        }

        // Attach cuboids to each bone
        for (BoneJson boneJson : json.bones()) {
            ModelPart part = bones.get(boneJson.name());
            for (CubeJson cube : boneJson.cubes()) {
                part.addCuboid(
                    cube.origin()[0],
                    cube.origin() [reddit](https://www.reddit.com/r/fabricmc/comments/rsairs/how_do_i_export_a_entity_model_from_blockbench_to/),
                    cube.origin() [mcreator](https://mcreator.net/forum/92093/can-blockbench-export-required-model-format-java-creatures),
                    (int) cube.size()[0],
                    (int) cube.size() [reddit](https://www.reddit.com/r/fabricmc/comments/rsairs/how_do_i_export_a_entity_model_from_blockbench_to/),
                    (int) cube.size() [mcreator](https://mcreator.net/forum/92093/can-blockbench-export-required-model-format-java-creatures),
                    new Dilation(0.0f),
                    cube.mirror(),
                    (int) cube.uv()[0],
                    (int) cube.uv() [reddit](https://www.reddit.com/r/fabricmc/comments/rsairs/how_do_i_export_a_entity_model_from_blockbench_to/)
                );
            }
        }

        // Build the hierarchy
        BoneJson rootBoneJson = json.bones().stream()
            .filter(b -> b.name().equals("root"))
            .findFirst().orElseThrow();
        this.root = bones.get(rootBoneJson.name());

        for (BoneJson boneJson : json.bones()) {
            ModelPart part = bones.get(boneJson.name());
            if (boneJson.default_rotation() != null) {
                float[] r = boneJson.default_rotation();
                part.pitch = r[0];
                part.yaw = r [reddit](https://www.reddit.com/r/fabricmc/comments/rsairs/how_do_i_export_a_entity_model_from_blockbench_to/);
                part.roll = r [mcreator](https://mcreator.net/forum/92093/can-blockbench-export-required-model-format-java-creatures);
            }
            float[] pivot = boneJson.pivot();
            part.setPivot(pivot[0], pivot [reddit](https://www.reddit.com/r/fabricmc/comments/rsairs/how_do_i_export_a_entity_model_from_blockbench_to/), pivot [mcreator](https://mcreator.net/forum/92093/can-blockbench-export-required-model-format-java-creatures));

            for (String childName : boneJson.children()) {
                part.addChild(childName, bones.get(childName));
            }
        }
    }

    @Override
    public void setAngles(SpyroEntity entity, float limbAngle, float limbDistance,
                          float animationProgress, float headYaw, float headPitch) {
        // Here you modify bones.get("head").yaw/pitch, wings, tail, etc. based on animations
        // Possibly driven by a second animation JSON file.
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices,
                       int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public Identifier getTextureId() {
        return textureId;
    }
}
```

This follows the same dynamic‑model ideas as the Fabric custom model tutorial but applied to entities. [wiki.fabricmc](https://wiki.fabricmc.net/tutorial:custom_model)

### 3.3. Renderer

A minimal renderer:

```java
public class SpyroRenderer extends MobEntityRenderer<SpyroEntity, JsonDrivenModel> {
    private static final Identifier SPYRO_JSON =
        new Identifier("modid", "models/entity/spyro.json");

    public SpyroRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, loadModel(), 0.7F);
    }

    private static JsonDrivenModel loadModel() {
        // Load JSON from resource manager at init time
        MinecraftClient client = MinecraftClient.getInstance();
        Resource res = client.getResourceManager().getResource(SPYRO_JSON).orElseThrow();
        try (Reader reader = res.getReader()) {
            SpyroModelJson json = GSON.fromJson(reader, SpyroModelJson.class);
            return new JsonDrivenModel(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Identifier getTexture(SpyroEntity entity) {
        return this.getModel().getTextureId();
    }
}
```

You register this renderer in your Fabric client initializer:

```java
EntityRendererRegistry.register(ModEntities.SPYRO, SpyroRenderer::new);
```

---

## 4. Keeping Java and JSON in sync

Given your schema‑driven workflow, I’d recommend:

- Treat the **Blockbench `.bbmodel` + exported Bedrock geo JSON** as the source of truth for geometry. [reddit](https://www.reddit.com/r/Blockbench/comments/n3hfok/json_model/)
- Write a small Python or Java tool that:
  - Reads Bedrock geo JSON.
  - Emits:
    - Your custom `SpyroModelJson` (trimmed fields, normalized pivots).
    - Optional helper snippets for a hand‑edited Java model (e.g., generating the `getTexturedModelData` body).
- Use the **plain Java model**:
  - For debugging and fallback (easy to set a breakpoint, see cubes).
  - For potential non‑JSON entities or performance‑critical versions.
- Use the **JSON‑driven model**:
  - For main content, where you want to tweak bones or UVs without recompiling.
  - For elemental variants: same JSON structure, different `texture` values pointing to your poison/ice/magma/supercharge textures. [ppl-ai-file-upload.s3.amazonaws](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/images/9838088/2377301c-f214-4c34-862b-02697c94c997/T_CPS1999_Spyro_SuperCharge_E.jpg)

If you’d like, I can next sketch a concrete converter spec: “take Blockbench Bedrock geo, output your custom JSON plus a code‑gen’d Java `getTexturedModelData`”, in either Java or Python to slot into your existing toolchain.
