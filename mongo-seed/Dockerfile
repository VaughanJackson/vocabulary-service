FROM mongo

COPY CharFreq-Modern.csv /CharFreq-Modern.csv

# Load vocabulary from Jun Da's CSV file.
# NOTE entire vocabulary DROPPED AND RECREATED each time (--drop).
CMD mongoimport  \
    --host mongodb \
    --db vocabulary \
    --collection vocabulary \
    --drop \
    --type csv \
    --headerline \
    --file /CharFreq-Modern.csv

COPY calculateFrequency.js /calculateFrequency.js

# Calculate the character frequency percentage for each character in the vocabulary.
CMD mongo --host mongodb vocabulary /calculateFrequency.js