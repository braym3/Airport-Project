import {
    ProgramBox,
    ProgramContent,
    ProgramFlex,
    ProgramStack,
    ProgramTitle,
    ProgramText,
    ProgramImage,
    useProgram
} from "planby";

export const ProgramItem = ({ program, ...rest }) => {
    const {
        styles,
        formatTime,
        set12HoursTimeFormat,
        isLive,
        isMinWidth
    } = useProgram({
        program,
        ...rest
    });

    const { data } = program;
    const { image, title, since, till, description } = data;

    const sinceTime = formatTime(since, set12HoursTimeFormat()).toLowerCase();
    const tillTime = formatTime(till, set12HoursTimeFormat()).toLowerCase();

    return (
        <ProgramBox width={styles.width} style={styles.position} onClick={() => console.log(`${title}`)}>
            <ProgramContent width={styles.width} isLive={isLive}>
                <ProgramFlex>
                    {isLive && isMinWidth && <ProgramImage src={image} alt="Preview" />}
                    <ProgramStack>
                        <ProgramTitle><img src={image} id={"time-slot-icon"} alt={"event icon"}/> {title}</ProgramTitle>
                        <ProgramText>
                            {description}
                        </ProgramText>
                        <ProgramText>
                            {sinceTime} - {tillTime}
                        </ProgramText>
                    </ProgramStack>
                </ProgramFlex>
            </ProgramContent>
        </ProgramBox>
    );
};
