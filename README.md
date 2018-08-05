Runscanner
===
 Runscanner is a OCR app using ML Kit Text Recognition model.

This app helps runner going to [Club 16](https://www.trevorlindenfitness.com/downtown-vancouver/).
- Scan your running from picture.
- Record your running with card.
- Take statistics of your running

## Demo
<p align="center"><img src="https://github.com/mizushou/RunScanner/blob/media/gif/runscanner_ocr.gif" alt="RunScanner ocr" height="600px"></p>

## Screenshots
<p align="center">
<img src="https://github.com/mizushou/RunScanner/blob/media/image/runscanner_stats_thisweek.png" width="280"/> <img src="https://github.com/mizushou/RunScanner/blob/media/image/runscanner_stats_thismonth.png" width="280"/> <img src="https://github.com/mizushou/RunScanner/blob/media/image/runscanner_stats_thisyear.png" width="280"/>
</p>

## App theme

### Font

1. Roboto Condensed
    -  Character
1.  Eczar
    - Numerical character

### Color
1. Primary
    - #1EB980
1. PrimaryVariant
    - #045D56
1. Secondary
    - 3B4859
1. Background
    - #37474f
1. Surface
    - #455a64
1. OnPrimary
    - #FFFFFF
1. OnSecondary
    - #000000
1. OnBackground
    - #FFFFFF
1. OnSurface
    - #FFFFFF

## Data
### Table#1 [runs]
| No. | Column name        | Type    | Unit                   |
|:----|:-------------------|:--------|:-----------------------|
| 1   | __id               |         |                        |
| 2   | uuid               |         |                        |
| 3   | distance           | REAL    | meter                  |
| 4   | calorie            | INTEGER | kcal                   |
| 5   | duration           | INTEGER | second                 |
| 6   | average_pace       | INTEGER | second/mile            |
| 7   | average_heart_rate | INTEGER | bpm                    |
| 8   | date               | INTEGER | millisecond(unix time) |

### Model#1 [Run]
| No. | Attribute name | Type   | Unit                   | Meaning            |
|:----|:---------------|:-------|:-----------------------|:-------------------|
| 1   | mId            | UUID   |                        |        uuid            |
| 2   | mDistance      | double | meter                  | run distance       |
| 3   | mCalorie       | int    | kcal                   | used calories      |
| 4   | mDuration      | long   | second                 | running time       |
| 5   | mAvePace       | long   | second/mile            | average pace       |
| 6   | mAveHeartRate  | int    | bpm                    | average heart rate |
| 7   | mDate          | long   | millisecond(unix time) | taken date         |

### SQLite
1. select
    1. full search
        - ```select * from runs;```
    2. with where clause
        1. from ${BEGIN}
            - ```select * from runs where date >= ${BEGIN};```
        2. before ${END}
            - ```select * from runs where date < ${END};```
        3. from ${BEGIN} and before ${BEGIN}
            - ```select * from runs where date >= ${BEGIN} and date < ${END};```
1. count
    1. full count
        1. ```select count(*) from runs;```
    2. with where clause
        1. from ${BEGIN}
            - ```select count(*) from runs where date >= ${BEGIN};```
        2. before ${END}
            - ```select count(*) from runs where date < ${END};```
        3. from ${BEGIN} and before ${BEGIN}
            - ```select count(*) from runs where date >= ${END} and date < ${BEGIN};```

- total
    1. full total
        - ```total(${COLUMN NAME})```
    2. with where clause
        1. from ${BEGIN}
            - ```select total(${COLUMN NAME}) from runs where date >= ${BEGIN};```
        2. before ${END}
            - ```select total(${COLUMN NAME}) from runs where date < ${BEGIN};```
        3. from ${BEGIN} and before ${BEGIN}
            - ```select total(${COLUMN NAME}) from runs where date >= ${END} and date < ${BEGIN};```
- insert
    - ```insert into runs(uuid, distance, calorie, duration, average_pace, average_heart_rate, date) values('${UUID}', ${DISTANCE}, ${CALORIE}, ${DURATION}, ${AVERAGE_PACE}, ${AVERAGE_HEART_RATE}, ${DATE});```
