FROM mongo

COPY CharFreq-Modern.csv /CharFreq-Modern.csv
COPY wait-for-it.sh /wait-for-it.sh
COPY build-vocab.sh /build-vocab.sh
COPY calculateFrequency.js /calculateFrequency.js

RUN chmod +x /wait-for-it.sh
RUN chmod +x /build-vocab.sh

