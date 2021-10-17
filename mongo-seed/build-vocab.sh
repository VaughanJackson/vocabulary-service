#!/usr/bin/env bash
# Script that populates the Mongo DB vocabulary DB from Jun Da's character frequencies CSV file.

echo "Loading Jun Da's character frequencies..."

# Load vocabulary from Jun Da's CSV file.
# NOTE entire vocabulary DROPPED AND RECREATED each time (--drop).
mongoimport  \
    --host mongodb \
    --db vocabulary \
    --collection vocabulary \
    --drop \
    --type csv \
    --headerline \
    --file /CharFreq-Modern.csv

echo "Calculating character frequency percentages..."

# Calculate the character frequency percentage for each character in the vocabulary.
mongo --host mongodb vocabulary /calculateFrequency.js
