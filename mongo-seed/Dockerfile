FROM mongo

COPY CharFreq-Modern.csv /CharFreq-Modern.csv

# NOTE entire vocabulary DROPPED AND RECREATED each time (--drop).
CMD mongoimport  \
    --host mongodb \
    --db vocabulary \
    --collection vocabulary \
    --drop \
    --type csv \
    --headerline \
    --file /CharFreq-Modern.csv