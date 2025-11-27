#!/bin/bash
# This script loads default images into the application.

DEFAULT_IMAGES_DIR="$HOME/volando-app/images/default/"

# Check if the default images directory exists
if [ ! -d "$DEFAULT_IMAGES_DIR" ]; then
  echo "Default images directory does not exist: $DEFAULT_IMAGES_DIR"
  echo "Creating default images directory ..."
  mkdir -p "$DEFAULT_IMAGES_DIR"
else
  echo "Default images directory already exists: $DEFAULT_IMAGES_DIR"
  exit 0
fi

# Create a temporary directory to download images
echo "Creating temporary directory for image downloads ..."
TEMP_DIR=$(mktemp -d)
cd "$TEMP_DIR"

# List of default images to download
IMAGE_URLS=(
  "user.png|https://static.thenounproject.com/png/4154905-200.png"
  "flight.jpg|https://img.freepik.com/premium-vector/airplane-icon-continuous-one-line-draw-flying-plane-minimalist-vector-design-white-background_533993-9676.jpg"
  "flight_route.jpg|https://img.freepik.com/premium-vector/minimalist-location-pins-vector-continuous-line-drawing-navigation-route-illustration-map-concept_213497-3516.jpg"
)

# Download each image and save with the specified file name
echo "Downloading default images ..."
for imageDetails in "${IMAGE_URLS[@]}"; do
  IFS="|" read -r fileName url <<< "$imageDetails"
  wget -O "$fileName" "$url"
done

# Move downloaded images to the default images directory
echo "Moving downloaded images to $DEFAULT_IMAGES_DIR ..."
mv *.png *.jpg "$DEFAULT_IMAGES_DIR"


# Clean up temporary directory
echo "Cleaning up temporary files ..."
cd ..
rm -rf "$TEMP_DIR"

echo "Default images have been loaded into: $DEFAULT_IMAGES_DIR"