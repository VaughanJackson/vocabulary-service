#!/usr/bin/env bash
# bootstrap.sh - bootstraps the vocabulary database

# Load vocabulary from Jun Da's CSV file.
mongoimport --db vocabulary \
            --collection vocabulary \
            --type csv \
            --headerline \
            --file mongo-seed/CharFreq-Modern.csv

# Calculate the character frequency percentage for each character in the vocabulary.
mongo vocabulary mongo-seed/calculateFrequency.js
