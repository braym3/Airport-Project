import { ChannelBox, ChannelLogo } from "planby";

export const ChannelItem = ({ channel }) => {
    const { position, logo, title } = channel;
    return (
        <ChannelBox {...position}>
            <div id={"sidebar-gate-block"}></div>
            <h3 id={"sidebar-gate-header"}>{title}</h3>
        </ChannelBox>
    );
};
