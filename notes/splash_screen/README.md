# Cómo activar el splash screen en development.

### Apretas donde dice `VolandoApp`, puede ser que en tu caso sea `Current File`

<img width="570" height="45" alt="image" src="https://github.com/user-attachments/assets/9a0afc38-b910-4543-bd7a-700975d98802" />

### Dale a `Edit configurations`

<img width="280" height="154" alt="image" src="https://github.com/user-attachments/assets/1b0c7996-12b2-4e34-8b0e-a43abfd4fc9b" />

### Apreta donde dice `Add new run configuration` o `Agregar nueva configuración de ¿lanzamiento?`. Selecciona `Application` si te lo pide.

### Rellenalo más o menos así. (Lo del -splash aún no)
> La parte importante es que la Main Class sea app.VolandoApp y que corra en Local.

<img width="798" height="685" alt="image" src="https://github.com/user-attachments/assets/5189aab5-102f-449c-8add-ff388104b9c1" />

### Para añadir la parte del -splash. Vamos a donde dice `Modify options` a la derecha de Build and Run.
### Ahí apretamos en `Add VM Options` o `Añadir opciones de VM`

<img width="405" height="365" alt="image" src="https://github.com/user-attachments/assets/297f0c68-33a0-4782-b7ed-c64d98820434" />

### En esa nueva linea de texto, de VMs Options, vamos a añadir ese comando
> Obviamente cambien el nombre por el nombre y la extension por la extension.

```
-splash:src/main/resources/<nombre_imagen>.<extension_imagen>
```

### Y listo, corran el programa con la nueva configuración.

