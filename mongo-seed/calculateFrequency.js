// calculateFrequency.js - Uses a MongoDB aggregate pipeline to calculate the frequency (percentage) of each character.

// Calculate '频率(%)' as the difference between the character's cumulative frequency and that of the character before it
// truncating to 2 dp - ideally this would be rounding to 2 dp.
calculateFrequency = {
    $divide: [
        { $trunc: {
                $multiply: [
                    { $subtract: ['$累计频率(%)', '$上.累计频率(%)'] },
                    100]
            }
        }, 100]
};

// Sort by the 序列号 ('character order number').
sortByCharacterOrderNumber = { $sort: { 序列号: 1 } };

// Project characters to remove the _id field and add the key to allow each character to be joined to
// the character preceding it.
removeIdAndAddPreviousCharacterOrderNumber =
    { $project: {
            _id: 0, // remove _id
            序列号: 1,
            汉字: 1,
            频率: 1,
            '累计频率(%)': 1,
            拼音: 1,
            英文翻译: 1,
            上序列号: {    // add key for join to get previous cumulative frequency
                $cond: {
                    if: { $eq: ['$序列号', 1] },
                    then: '$序列号', // special case for first character so we don't lose it as a result of the join
                    else: { $subtract: ['$序列号', 1] }
                }
            }
        }};

// Join vocabulary to itself to get previous cumulative frequency % (累计频率(%)) for each character.
// 上 becomes an array of the previous character's fields at this stage.
joinPreviousCharacter =
    { $lookup: {
            from: 'vocabulary',
            localField: '上序列号',
            foreignField: '序列号',
            as: '上'
        }};

// Unwind the 上 array so that fields from the previous character can be referred to in calculateFrequency
// within the next (addCalculatedCharacterFrequency) stage.
unwindPreviousCharacter = { $unwind: '$上' };

// Add character frequency % (频率(%)) as a calculated field.
addCalculatedCharacterFrequency =
    { $addFields: {
            '频率(%)': {
                $cond: {
                    if: { $eq: ['$序列号', 1] },
                    then: '$累计频率(%)', // special case for first character because there is no previous cumulative frequency
                    else: calculateFrequency
                }
            }
        }};

// Remove the previous character (上) fields we no longer want.
removePreviousCharacter = { $project: { 上: 0} };

// Overwrite the previous vocabulary with the product of the pipeline.
updateVocabulary = { $out: 'vocabulary' };


// Pipeline to calculate and add a character frequency % field for each character in the vocabulary.
db.vocabulary.aggregate(
    [
        sortByCharacterOrderNumber,
        /* { $limit: 5 },  comment in to speed up troubleshooting */
        removeIdAndAddPreviousCharacterOrderNumber,
        joinPreviousCharacter,
        unwindPreviousCharacter,
        addCalculatedCharacterFrequency,
        removePreviousCharacter,
        updateVocabulary
    ]
).forEach(printjson); /* comment out updateVocabulary stage and comment in this printjson to see outcome of pipeline. */
