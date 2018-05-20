#!/usr/bin/env bash
# bootstrap.sh - bootstraps the vocabulary database

mongoimport --db vocabulary \
            --collection vocabulary \
            --type csv \
            --headerline \
            --file mongo-seed/CharFreq-Modern.csv
